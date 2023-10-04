package com.example.routes

import io.ktor.server.routing.*
import com.example.models.*
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
//import com.mongodb.kotlin.client.FindIterable
import database
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable
import org.bson.BsonDocument
import org.bson.conversions.*
import org.bson.types.*
import org.litote.kmongo.*
import javax.management.Query.eq
import org.mongojack.*

val collection = database.getCollection<Customer>(collectionName = "Customer")
var filter: Bson = BsonDocument()



fun Route.customerRouting() {
    route("/all-customers") {
        get {
            val allCustomers = customerStorage
            call.respond(collection.find<Customer>(filter).toList())
        }
        get("/customer") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val queryParams = Filters.eq("id",id)
            call.respond(collection.find<Customer>(queryParams).toString())
            FindIterable<Customer>(Customer) = col.find(Filters.eq("id", id)
        }
        post {
            val customer = call.receive<Customer>()
                collection.insertOne(customer).also {
                    call.respondText(
                        "Customer stored correctly.",
                        status = HttpStatusCode.Created
                    )
            }

        }
/*        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (customerStorage.removeIf { it.id == id }) {
                call.respondText("Customer removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }*/
    }
}