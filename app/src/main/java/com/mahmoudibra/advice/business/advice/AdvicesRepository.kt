package com.mahmoudibra.advice.business.advice

import com.mahmoudibra.advice.source.remote.Repository
import com.mahmoudibra.advice.source.remote.Result

interface AdvicesRepository : Repository {
    suspend fun fetchAdvice(): Result<AdviceModel>
}
