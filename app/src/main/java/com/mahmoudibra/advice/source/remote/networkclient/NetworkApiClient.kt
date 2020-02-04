package com.mahmoudibra.advice.source.remote.networkclient

import com.mahmoudibra.advice.BuildConfig
import java.util.concurrent.TimeUnit
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkApiClient {
    private var retrofit: Retrofit? = null
    val apiClient: Retrofit
        get() {
            if (retrofit == null) {
                retrofit =
                    makeRetrofit()
            }
            return retrofit!!
        }

    private fun makeRetrofit(vararg interceptors: Interceptor): Retrofit {
        val url: String = BuildConfig.BASE_URL
        return Retrofit.Builder()
            .baseUrl(url)
            .client(makeHttpClient(interceptors))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private fun makeHttpClient(interceptors: Array<out Interceptor>) = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor())
        .apply { interceptors().addAll(interceptors) }
        .followRedirects(true)
        .followSslRedirects(true)
        .retryOnConnectionFailure(true)
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .cache(null)
        .build()

    private fun loggingInterceptor() = HttpLoggingInterceptor().apply {
        level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }
}
