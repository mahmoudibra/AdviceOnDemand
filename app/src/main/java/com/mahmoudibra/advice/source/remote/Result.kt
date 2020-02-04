package com.mahmoudibra.advice.source.remote

import com.mahmoudibra.advice.business.models.ErrorResponse

sealed class Result<out T> {

    data class Success<out T>(val data: T) : Result<T>()
    data class SystemError(val error: ErrorResponse) : Result<Nothing>()
    data class ServerError(val exception: Exception) : Result<Nothing>()

    companion object {
        fun <T> success(data: T): Result<T> =
            Success(data)

        fun systemError(error: ErrorResponse): Result<Nothing> =
            SystemError(error)

        fun serverError(exception: Exception): Result<Nothing> =
            ServerError(exception)
    }
}
