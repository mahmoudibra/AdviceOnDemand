package com.mahmoudibra.advice.source.remote.repoimpl

import com.mahmoudibra.advice.business.advice.AdviceModel
import com.mahmoudibra.advice.business.advice.AdvicesRepository
import com.mahmoudibra.advice.source.remote.Result
import com.mahmoudibra.advice.source.remote.endpoint.AdviceEndpoints
import com.mahmoudibra.advice.source.remote.networkclient.NetworkApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AdviceRepositoryImpl : AdvicesRepository {

    private val trendingEndpoint: AdviceEndpoints by lazy {
        NetworkApiClient.apiClient.create(AdviceEndpoints::class.java)
    }

    override suspend fun fetchAdvice(): Result<AdviceModel> {
        return request {
            withContext(Dispatchers.IO) {

                trendingEndpoint.fetchAdvice()
            }
        }
    }
}
