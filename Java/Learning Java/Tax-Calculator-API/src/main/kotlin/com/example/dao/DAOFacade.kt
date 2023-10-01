package com.example.dao

import com.example.models.User
import com.example.models.userResponse

interface DAOFacade {
        suspend fun alluserResponse(): List<User>?
        suspend fun userResponse(firstName: String): List<User>
        suspend fun addNewuserResponse(firstName: String,finalIncome: Double,finalTax: Double,amtToSnit: Double): List<User>?
    }
