package impl.service

import core.service.PublicationService
import domain.model.Publication
import java.util.*

class PublicationServiceImpl: PublicationService {

    private var publications: ArrayList<Publication> = ArrayList<Publication>()
    private var idGenerator: Int = 0

    override fun createPublication(text: String): Publication {
        var publication = Publication(idGenerator++, text, Date().time, Date().time)
        publications.add(publication)
        return publication
    }

    override fun readPublication(id: Int): Publication {
        val publication = publications.filter { it.id == id }.getOrNull(0)

        publication?.let {
            return publication
        }

        throw RuntimeException("No such publication")
    }

    override fun updatePublication(id: Int, text: String): Publication {
        val publication = readPublication(id)

        publication.text = text

        return publication
    }

    override fun deletePublication(id: Int): Publication {
        val publication = readPublication(id)
        publications.remove(publication)
        return publication
    }

    override fun index(from: Int, to: Int): List<Publication> {
        return publications.slice(from..to)
    }

    override fun index(): List<Publication> {
        return publications
    }

}