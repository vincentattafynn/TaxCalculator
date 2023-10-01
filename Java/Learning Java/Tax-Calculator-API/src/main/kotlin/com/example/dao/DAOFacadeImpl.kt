package com.example.dao

import com.example.dao.*
import com.example.models.userResponse
import com.example.models.*
import org.jetbrains.exposed.sql.*

class DAOFacadeImpl : DAOFacade {

    private fun resultRowTouserResponse(row: ResultRow) = User(
        firstName = row[userResponse.firstName],
        finalIncome = row[userResponse.finalIncome],
        finalTax = row[userResponse.finalTax],
        amtToSnit = row[userResponse.amtToSnit]
    )


    override suspend fun alluserResponse(): List<User> = dbQuery<List<User>> {
        userResponse.selectAll().map(::resultRowTouserResponse)
    }

    override suspend fun userResponse(firstName: String): List<User> = dbQuery<List<User>> {
        userResponse
            .select { userResponse.firstName eq firstName }
            .map(::resultRowTouserResponse)
    }


    override suspend fun addNewuserResponse(
        firstName: String,
        finalIncome: Double,
        finalTax: Double,
        amtToSnit: Double
    ): List<User> {
        val insertStatement = userResponse.insert {
            it[userResponse.firstName]
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowTouserResponse)
        return emptyList()
    }
}


