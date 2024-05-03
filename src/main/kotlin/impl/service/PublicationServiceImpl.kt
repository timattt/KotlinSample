package impl.service

import core.service.PublicationService
import domain.model.Publication
import impl.dao.PublicationDAO

class PublicationServiceImpl: PublicationService {

    private var dao: PublicationDAO = PublicationDAO()

    override suspend fun createPublication(text: String): Publication {
        return dao.add(text) ?: throw RuntimeException("No publication")
    }

    override suspend fun readPublication(id: Int): Publication {
        return dao.get(id) ?: throw RuntimeException("No publication")
    }

    override suspend fun deletePublication(id: Int): Publication {
        val publication = readPublication(id)
        dao.delete(id)
        return publication
    }

    override suspend fun index(): List<Publication> {
        return dao.all()
    }

}