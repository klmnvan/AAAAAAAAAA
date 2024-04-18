package com.example.prodjectformc.api

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class PostsRepositoryImpl(
    private val api: PostsService
) : PostsRepository {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun sendCodeEmail(email: String): Flow<Result<String>> {
        return flow {
            val response = try {
                api.sendCode(email)
            } catch (e: IOException) {
                e.printStackTrace()
                emit( Result.Error(message = "Error loading products"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            }
            emit(Result.Success(response))
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun checkCodeEmail(email: String, code: String): Flow<Result<String>> {
        return flow {
            val response = try {
                api.signIn(email, code)
            } catch (e: IOException) {
                e.printStackTrace()
                emit( Result.Error(message = "Error loading products"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            }
            emit(Result.Success(response))
        }
    }

}