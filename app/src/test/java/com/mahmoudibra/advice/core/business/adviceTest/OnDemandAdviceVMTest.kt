package com.mahmoudibra.advice.core.business.adviceTest

import com.google.common.truth.Truth
import com.mahmoudibra.MainCoroutineRule
import com.mahmoudibra.advice.business.advice.AdviceModel
import com.mahmoudibra.advice.business.advice.AdvicesRepository
import com.mahmoudibra.advice.business.advice.OnDemandAdviceVM
import com.mahmoudibra.advice.business.models.ErrorResponse
import com.mahmoudibra.advice.source.remote.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.io.EOFException
import java.lang.Exception
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test



class OnDemandAdviceVMTest {


    // Subject under test
    private lateinit var onDemandAdviceVM: OnDemandAdviceVM
    private var advicesRepository: AdvicesRepository = mockk()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupViewModel() {
        onDemandAdviceVM =
            OnDemandAdviceVM(
                adviceRepository = advicesRepository
            )
    }


    @Test
    fun `Fetch advice On Start And Return data successfully`() {
        val adviceFakeResponse = AdviceModel(fortune = listOf("Advice one"))
        val result = Result.success(adviceFakeResponse)
        coEvery { advicesRepository.fetchAdvice() } answers { result }

        onDemandAdviceVM.hydrate()

        Truth.assertThat(onDemandAdviceVM.advice.get())
            .isEqualTo(adviceFakeResponse.fortune[0])
        coVerify(exactly = 1) { advicesRepository.fetchAdvice() }
    }

    @Test
    fun `Fetch Advice Repos On Start And return System Exception`() {
        val adviceFakeResponse = AdviceModel(fortune = listOf("Advice one"))
        val successResult = Result.success(adviceFakeResponse)

        val result = Result.systemError(error = ErrorResponse("Custom BackEnd Exception"))
        coEvery { advicesRepository.fetchAdvice() } answers { result } andThen { successResult }
        onDemandAdviceVM.hydrate()

        Truth.assertThat(onDemandAdviceVM.advice.get())
            .isEqualTo("Advice one")
        coVerify(exactly = 2) { advicesRepository.fetchAdvice() }
    }

    // having an issue returning Exception from the Api Call with coroutines
    @Test
    fun `Fetch Advice Repos On Start And return Server Exception then return success result`() {
        val adviceFakeResponse = AdviceModel(fortune = listOf("Advice one"))
        val successResult = Result.success(adviceFakeResponse)
        coEvery { advicesRepository.fetchAdvice() } answers { Result.serverError(exception = Exception()) } andThen { successResult }

        onDemandAdviceVM.hydrate()

        Truth.assertThat(onDemandAdviceVM.advice.get())
            .isEqualTo("Advice one")
        coVerify(exactly = 2) { advicesRepository.fetchAdvice() }
    }

    // having an issue returning Exception from the Api Call with coroutines
    @Test
    fun `Fetch Advice Repos On Start And return EOFException Exception`() {
        coEvery { advicesRepository.fetchAdvice() } answers { Result.serverError(exception = EOFException()) }

        onDemandAdviceVM.hydrate()

        Truth.assertThat(onDemandAdviceVM.advice.get())
            .isEqualTo("a penny saved is a penny earned")
        coVerify(exactly = 1) { advicesRepository.fetchAdvice() }
    }
}
