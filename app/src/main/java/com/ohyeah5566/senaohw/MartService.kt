package com.ohyeah5566.senaohw

import com.squareup.moshi.JsonClass
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

//因為Api result有用data包起來 所以這邊也用BaseResponse
@JsonClass(generateAdapter = true)
data class BaseResponse<T>(
    val data: T
)

interface MartService {
    @GET("marttest.jsp")
    suspend fun getMartList(): BaseResponse<List<Mart>>
}

fun getServiceInstance() = service

private val service: MartService by lazy {

    val httpLogging = HttpLoggingInterceptor()
    val httpClient = OkHttpClient.Builder().addInterceptor(
        httpLogging
    ).build()

    Retrofit.Builder()
        .baseUrl("https://m.senao.com.tw/apis2/test/")
        .client(httpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(MartService::class.java)
}