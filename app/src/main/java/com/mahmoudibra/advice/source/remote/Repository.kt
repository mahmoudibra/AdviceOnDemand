package com.mahmoudibra.advice.source.remote

import com.mahmoudibra.advice.business.models.ErrorResponse
import com.mahmoudibra.advice.source.remote.networkclient.ErrorResponseConverter
import retrofit2.Response
import timber.log.Timber

interface Repository {
    suspend fun <T> request(call: suspend () -> Response<T>): Result<T> {
        return try {
            val response = call()
            return if (response.isSuccessful) {
                Result.success(response.body()) as Result<T>
            } else {
                Timber.e("ServerError With Network Operation With Code ${response.code()}")
                val errorModel = response.errorBody()?.let { errorBody ->
                    ErrorResponseConverter.parseError(errorBody)
                }
                Result.systemError(errorModel ?: ErrorResponse())
            }
        } catch (e: Exception) {
            Timber.e("ServerError In Network with Exception : $e")
            Result.serverError(e)
        }
    }
}
