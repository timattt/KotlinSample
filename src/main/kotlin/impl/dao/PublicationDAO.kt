package impl.dao

import domain.model.Publication
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class PublicationDAO() {

    object Publications : Table() {
        val id = integer("id").autoIncrement()
        val text = varchar("text", 128)
        val creationTime = long("creationTime")
        val lastModifyTime = long("lastModifyTime")

        override val primaryKey = PrimaryKey(id)
    }

    private var database: Database = Database.connect(
        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
        user = "root",
        driver = "org.h2.Driver",
        password = ""
    )

    init {
        transaction(database) {
            SchemaUtils.create(Publications)
        }
    }

    private fun resultRowToPublication(row: ResultRow) = Publication(
        id = row[Publications.id],
        text = row[Publications.text],
        lastModifyTime = row[Publications.lastModifyTime],
        creationTime = row[Publications.creationTime],
    )

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun get(id: Int): Publication? = dbQuery {
        Publications
            .select { Publications.id eq id }
            .map(::resultRowToPublication)
            .singleOrNull()
    }

    suspend fun add(text: String): Publication? = dbQuery {
        val insertStatement = Publications.insert {
            it[Publications.text] = text
            it[Publications.lastModifyTime] = System.currentTimeMillis()
            it[Publications.creationTime] = System.currentTimeMillis()
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToPublication)
    }

    suspend fun delete(id: Int): Boolean = dbQuery {
        Publications.deleteWhere { Publications.id eq id } > 0
    }

    suspend fun all(): List<Publication> = dbQuery {
        Publications.selectAll().map(::resultRowToPublication)
    }

}