package core.service

import domain.model.Publication

interface PublicationService {

    suspend fun createPublication(text: String): Publication
    suspend fun readPublication(id: Int): Publication
    suspend fun deletePublication(id: Int): Publication
    suspend fun index(): List<Publication>

}