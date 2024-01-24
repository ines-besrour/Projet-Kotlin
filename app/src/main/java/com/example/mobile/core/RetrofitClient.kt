package com.example.mobile.core

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {


    private const val BASE_URL = "https://ea16-197-26-245-109.ngrok-free.app/"

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
        val token = SharedPreferencesManager.getString("token","")
        if(token!=""){
            val requestWithHeader = chain.request()
                .newBuilder()
                .header(
                    "Authorization", "Bearer $token"
                ).build()
            return chain.proceed(requestWithHeader)
        }
        return chain.proceed(chain.request())

    }
}

object SharedPreferencesManager {

    private const val PREF_NAME = "SHARED_PREF"
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

}