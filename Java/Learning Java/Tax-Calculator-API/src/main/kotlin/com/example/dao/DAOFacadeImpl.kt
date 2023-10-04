package com.example.dao

import com.example.models.UserResponse
import com.example.models.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOFacadeImpl : DAOFacade {

    private fun resultRowTouserResponse(row: ResultRow) = User(
        firstName = row[UserResponse.firstName],
        finalIncome = row[UserResponse.finalIncome],
        finalTax = row[UserResponse.finalTax],
        amtToSnit = row[UserResponse.amtToSnit]
    )


    override suspend fun alluserResponse(): List<User> = dbQuery<List<User>> {
        UserResponse.selectAll().map(::resultRowTouserResponse)
    }

    override suspend fun userResponse(firstName: String): List<User> = dbQuery<List<User>> {
        UserResponse
            .select { UserResponse.firstName eq firstName }
            .map(::resultRowTouserResponse)
    }


    override suspend fun addNewuserResponse(
        firstName: String,
        finalIncome: Double,
        finalTax: Double,
        amtToSnit: Double
    ):User? {
        val insertStatement = UserResponse.insert {
            it[UserResponse.firstName] = firstName
            it[UserResponse.finalIncome] = finalIncome
            it[UserResponse.finalTax] = finalTax
            it[UserResponse.amtToSnit] = amtToSnit
        }

        return insertStatement.resultedValues?.singleOrNull()?.let(::resultRowTouserResponse)
    }
}


