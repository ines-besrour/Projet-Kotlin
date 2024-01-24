package com.example.mobile.core

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {


    private const val BASE_URL = "https://abce-197-26-245-109.ngrok-free.app/v2/"

    private val okHttpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(AuthorizationInterceptor)
        .build()

    private val retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    val authService : AuthenticateApi by lazy { retrofit.create(AuthenticateApi::class.java) }

    val todoService : TodoApi by lazy { retrofit.create(TodoApi::class.java) }


}


object AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
//        val requestWithHeader = chain.request()
//            .newBuilder()
//            .header(
//                "Authorization", ``
//            ).build()
//        return chain.proceed(requestWithHeader)
    }
}