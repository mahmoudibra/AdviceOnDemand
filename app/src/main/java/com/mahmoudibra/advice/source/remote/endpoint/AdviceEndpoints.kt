package com.mahmoudibra.advice.source.remote.endpoint

import com.mahmoudibra.advice.business.advice.AdviceModel
import retrofit2.Response
import retrofit2.http.GET

interface AdviceEndpoints {
    @GET("/fortune")
    suspend fun fetchAdvice(): Response<AdviceModel>
}
