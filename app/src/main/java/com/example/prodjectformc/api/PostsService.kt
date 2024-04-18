package com.example.prodjectformc.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json

interface PostsService {
    suspend fun sendCode(userEmail: String): String?
    suspend fun signIn(userEmail: String, code: String): String?
    companion object {
        fun create(): PostsService {
            return PostsServiceImpl(
                client = HttpClient(Android){
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    install(ContentNegotiation){
                        json()
                    }
                }
            )
        }
    }
}