package web.mapper

import domain.model.Publication
import web.dto.PublicationDTO

class PublicationMapper {

    fun toDto(publication: Publication): PublicationDTO {
        return PublicationDTO(publication.id, publication.text, publication.creationTime, publication.lastModifyTime)
    }

    fun fromDto(publicationDto: PublicationDTO): Publication {
        return Publication(publicationDto.id, publicationDto.text, publicationDto.creationTime, publicationDto.lastModifyTime)
    }

}