package com.mahmoudibra.advice.source.remote.networkclient

import com.mahmoudibra.advice.business.models.ErrorResponse
import java.io.IOException
import okhttp3.ResponseBody

object ErrorResponseConverter {
    fun parseError(errorBody: ResponseBody): ErrorResponse = try {
        NetworkApiClient.apiClient.responseBodyConverter<ErrorResponse>(ErrorResponse::class.java, arrayOfNulls(0)).convert(errorBody)
                ?: ErrorResponse()
    } catch (e: IOException) {
        ErrorResponse()
    }
}
