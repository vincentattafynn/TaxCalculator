package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*

@Serializable
//data class User(val firstName: String, val finalIncome: Double,val finalTax: Double,val amtToSnit:Double)
//val userStorage = mutableListOf<User>()




data class User(val firstName: String, val finalIncome: Double, val finalTax: Double, val amtToSnit: Double)

object userResponse : Table() {
    val firstName = varchar("firstName",128)
    val finalIncome = double("finalIncome")
    val finalTax = double("finalTax")
    val amtToSnit = double("amtToSnit")

}