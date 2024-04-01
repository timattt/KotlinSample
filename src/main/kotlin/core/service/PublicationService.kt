package core.service

import domain.model.Publication

interface PublicationService {

    fun createPublication(text: String): Publication
    fun readPublication(id: Int): Publication
    fun updatePublication(id: Int, text: String): Publication
    fun deletePublication(id: Int): Publication
    fun index(from: Int, to: Int): List<Publication>
    fun index(): List<Publication>

}