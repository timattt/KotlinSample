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
        post("/update/{id}") {
            val id = call.parameters["id"]?.toInt()
            val text = call.request.queryParameters["text"]

            if (id == null || text == null) {
                throw RuntimeException("Bad parameters")
            }

            call.respond(HttpStatusCode.OK, publicationMapper.toDto(publicationService.updatePublication(id, text)))
        }
        get("/index") {
            val fromParam = call.request.queryParameters["from"]
            val toParam = call.request.queryParameters["to"]

            if (fromParam == null && toParam == null) {
                call.respond(HttpStatusCode.OK, publicationService.index().map { publicationMapper.toDto(it) })
            } else if (fromParam == null || toParam == null) {
                throw RuntimeException("Bad parameters")
            } else {
                val from = fromParam?.toInt()
                val to = toParam?.toInt()

                if (from == null || to == null) {
                    throw RuntimeException("Bad parameters")
                }

                call.respond(HttpStatusCode.OK, publicationService.index(from, to).map { publicationMapper.toDto(it) })
            }
        }
    }
}