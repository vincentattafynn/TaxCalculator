package com.example.models

import com.mongodb.client.MongoDatabase
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.litote.kmongo.getCollection
import com.example.routes.*
import database
import kotlin.random.Random

val collection = database.getCollection<Customer>(collectionName = "Customer")


fun generateRandomId(): String {
    val Length = 10
    val charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return (1..Length)
        .map { charset[Random.nextInt(0, charset.length)] }
        .joinToString("")
}

//data class Customer(val id: String, val firstName: String, val lastName: String, val email: String)

@Serializable
data class Customer(
    val id: String = generateRandomId(),
    val firstName: String,
    val lastName: String,
    val email: String
    )

@Serializable
val customerStorage = mutableListOf(collection.find<Customer>())


data class CreateCustomerDto(
    val firstName: String,
    val lastName: String,
    val email: String
)


