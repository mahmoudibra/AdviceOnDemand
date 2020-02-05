package com.mahmoudibra.advice.core.business.adviceTest

import com.google.common.truth.Truth
import com.mahmoudibra.MainCoroutineRule
import com.mahmoudibra.advice.business.advice.OnDemandAdviceVM
import com.mahmoudibra.advice.source.remote.repoimpl.AdviceRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import okhttp3.mockwebserver.MockResponse




class OnDemandAdviceVMIntegrationTest {

    var server = MockWebServer()

    // Subject under test
    private lateinit var onDemandAdviceVM: OnDemandAdviceVM
    private var advicesRepository: AdviceRepositoryImpl = AdviceRepositoryImpl()

    @Before
    fun setupMockedServerAndViewModel() {
        onDemandAdviceVM = OnDemandAdviceVM(adviceRepository = advicesRepository)
    }

    @After
    fun clearMockedServer() {
        server.shutdown()
    }

    @Test
    fun `Fetch advice On Start And Return data successfully`() {
        val response = "{\n" +
            "  \"fortune\": [\n" +
            "    \"Do not act as if you were going to live ten thousand years. Death hangs over\",\n" +
            "    \"you. While you live, while it is in your power, be good.\",\n" +
            "    \"        ― Marcus Aurelius, \\\"The Meditations\\\", Book IV\"\n" +
            "  ]\n" +
            "}"

        server.enqueue(MockResponse().setBody(response))
        server.start()
        val baseUrl = server.url("http://api.acme.international/fortune")

        onDemandAdviceVM.hydrate()

        val request1 = server.takeRequest()

        Truth.assertThat("http://api.acme.international/fortune")
            .isEqualTo(request1.path)
        Truth.assertThat(onDemandAdviceVM.advice.get())
            .isEqualTo("Do not act as if you were going to live ten thousand years. Death hangs over")
    }
}
