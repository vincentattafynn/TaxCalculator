package com.example.routes

import com.example.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.dao.*
import io.ktor.server.freemarker.*


const val ssnitPercentage: Double = 0.055
val chargeableIncome = arrayOf(420.00, 110.00, 130.00, 3000.00, 16395.00, 29963.00, 50000.00)
val rate = arrayOf(0.00, 0.05, 0.10, 0.175, 0.25, 0.30, 0.35)
val tax_pay = arrayOf(0.00, 5.50, 13.00, 525.00, 4098.75, 8988.90, 17500.00)
val cummIncome = arrayOf(412.00, 512.00, 642.00, 3642.00, 20037.00, 50000.00)
val cummTax = arrayOf(0.00, 5.50, 18.50, 543.80, 4642.25, 13631.15)

fun SSNIT_func(gross_income: Double): Double {
    //function to calculate amount paid to SSNIT
    val ssnitReturn = gross_income * ssnitPercentage
    return ssnitReturn
}

fun calculateTaxableIncome(ssnitReturn: Double, grossIncome: Double): Double {
    return grossIncome - ssnitReturn
}

fun getIncomeRange(grossIncome: Double, chargeableIncome: Array<Double>): Int {
    var smallest_max: Double? = null
    for (income in chargeableIncome) {
        if (grossIncome > income) {
            smallest_max = income
        }
    }
    return chargeableIncome.indexOf(smallest_max)
}

fun calcTax(taxableIncome: Double, arrayIndex: Int, cummTax: Array<Double>, rate: Array<Double>): Double {
    val remInc = taxableIncome - cummIncome[arrayIndex]
    val temp: Double = (remInc * rate[arrayIndex + 1])
    val finalTax = temp + cummTax[arrayIndex]
    return finalTax
}

fun calcIncome(grossIncome: Double, amtToSsnit: Double, finalTax: Double): Double {
    val temp = grossIncome - amtToSsnit
    return temp - ((finalTax ?: 0.0))
}
fun Route.calculateIncome() {
    route("/calculate-income") {
        get {
            try {
                //val getParameters = call.receiveParameters()
                val firstName = call.parameters["firstName"] ?: return@get call.respondText("Bad Request", status = HttpStatusCode.BadRequest)
                val grossIncomeStr = call.parameters["income"] ?: return@get call.respondText("Bad Request", status = HttpStatusCode.BadRequest)
                val grossIncome = grossIncomeStr?.toDoubleOrNull() ?: 0.0

                // Perform income calculations here (your existing code)

                val amtToSsnit = SSNIT_func(grossIncome)
                val taxableIncome = calculateTaxableIncome(amtToSsnit, grossIncome)
                val incomeIndex = getIncomeRange(taxableIncome, chargeableIncome)
                val finalTax = calcTax(taxableIncome, incomeIndex, cummTax, rate)
                val finaleIncome = calcIncome(grossIncome, amtToSsnit, finalTax)

                // Create a UserData object to store in your database
                val userData = User(firstName,finaleIncome,finalTax,amtToSsnit)

                // Store the user data in your database (you'll need a database connection)

                call.respond(userData)

                call.respond(FreeMarkerContent("/calculate-income", "userResults" ))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound, "Invalid input. Please check your request parameters.")
            }
        }
    }
}
