package web.dto

import kotlinx.serialization.Serializable

@Serializable
data class PublicationDTO(val id: Int, var text: String, val creationTime: Long, val lastModifyTime: Long)
