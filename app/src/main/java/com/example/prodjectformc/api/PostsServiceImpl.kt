package com.example.prodjectformc.api

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlin.math.sign

class PostsServiceImpl (private val client: HttpClient): PostsService {
    override suspend fun sendCode(userEmail: String): String? {
        return try {
            val response = client.post {
                url(HttpRoutes.SENDCODE)
                contentType(ContentType.Application.Json)
                headers {
                    append("User-email", userEmail)
                }
            }
            return if (response.status.isSuccess()){
                response.status.description
            } else {
                null
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
            null
        }
    }

    override suspend fun signIn(userEmail: String, code: String): String? {
        return try {
            val response = client.post {
                url(HttpRoutes.SIGNIN)
                contentType(ContentType.Application.Json)
                headers {
                    append("User-email", userEmail)
                    append("User-code", code)
                }
            }
            return if (response.status.isSuccess()){
                response.status.value.sign.toString()
            } else {
                null
            }
        } catch (e: RedirectResponseException) {
            Log.d("Error 3xx", e.response.status.description)
            e.response.status.description
        } catch (e: ClientRequestException) {
            Log.d("Error 4xx", e.response.status.description)
            e.response.status.description
        } catch (e: ServerResponseException) {
            Log.d("Error 5xx", e.response.status.description)
            e.response.status.description
        } catch (e: Exception) {
            println("Error: ${e.message}")
            e.message
        }
    }

}