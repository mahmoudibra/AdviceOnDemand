package com.mahmoudibra.advice.business.advice

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.mahmoudibra.advice.base.BaseVM
import com.mahmoudibra.advice.source.remote.Result
import com.mahmoudibra.advice.utils.wrapEspressoIdlingResource
import java.io.EOFException
import kotlinx.coroutines.launch

class OnDemandAdviceVM(
    private val adviceRepository: AdvicesRepository
) : BaseVM() {

    var advice: ObservableField<String> = ObservableField("")
    private val fallbackAdvice = "a penny saved is a penny earned"
    override fun hydrate() {
        fetchAdvice()
    }

    private fun fetchAdvice() {
        wrapEspressoIdlingResource {
            viewModelScope.launch {
                _inProgress.value = true
                val response =
                    adviceRepository.fetchAdvice()
                _inProgress.value = false
                when (response) {
                    is Result.Success -> {
                        advice.set(response.data.fortune[0])
                    }
                    is Result.SystemError -> {
                        advice.set(fallbackAdvice)
                        fetchAdvice()
                    }
                    is Result.ServerError -> {
                        advice.set(fallbackAdvice)
                        if (!(response.exception is EOFException)) {
                            fetchAdvice()
                        }
                    }
                }
            }
        }
    }
}
