package com.example.mobile.core

import com.example.mobile.core.dto.AuthenticateTodo
import com.example.mobile.core.dto.LoginDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticateApi {


    @POST("/user/login")
    fun login(@Body authDto : AuthenticateTodo): Call<LoginDto>

    @POST("/user/sign-up")
    fun signUp(@Body authDto : AuthenticateTodo): Call<Void>



}