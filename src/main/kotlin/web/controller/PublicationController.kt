package web.controller

import impl.service.PublicationServiceImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import web.mapper.PublicationMapper

var publicationService = PublicationServiceImpl()
var publicationMapper = PublicationMapper()

fun Application.configureRouting() {
    routing {
        post("/create") {
            call.request.queryParameters["text"]?.let {
                call.respond(HttpStatusCode.OK, publicationMapper.toDto(publicationService.createPublication(it)))
            }
        }
        get("/read/{id}") {
            call.parameters["id"]?.let {
                call.respond(HttpStatusCode.OK, publicationMapper.toDto(publicationService.readPublication(it.toInt())))
            }
        }
        post("/delete/{id}") {
            call.parameters["id"]?.let {
                call.respond(publicationMapper.toDto(publicationService.deletePublication(it.toInt())))
            }
        }
        get("/index") {
            call.respond(HttpStatusCode.OK, publicationService.index().map { publicationMapper.toDto(it) })
        }
    }
}