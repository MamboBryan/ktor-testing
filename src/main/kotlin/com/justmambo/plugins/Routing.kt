package com.justmambo.plugins

import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Serializable
data class Item(val name: String, val quantity: Int)

fun Application.configureRouting() {

    val database = mutableListOf<Item>()

    routing {

        get {
            call.respond("Hello World!")
        }

        route("orders"){
            get {
                call.respond(database)
            }

            post {
                val body = call.receive<Item>()
                database.add(body)
                call.respond("Saved Successfully")
            }

            get("/{name}"){
                val name = call.parameters["name"]
                val item = database.firstOrNull { it.name == name }
                if(item != null)
                    call.respond(item)
                else
                    call.respond(status = HttpStatusCode.NotFound, "No Item Found")
            }
        }
    }
}
