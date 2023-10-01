package com.example

import com.example.plugins.*
import io.ktor.server.application.*

import com.example.dao.*


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)

}

fun Application.module() {
    DatabaseFactory.init()
    configureSerialization()
    configureRouting()
    //configureTemplating()
}





