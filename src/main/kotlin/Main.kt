
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import web.controller.configureRouting

fun main() {
    embeddedServer(Netty, port = 7777, host = "0.0.0.0") {
        configureRouting()
        install(ContentNegotiation) {
            json()
        }
    }.start(wait = true)
}