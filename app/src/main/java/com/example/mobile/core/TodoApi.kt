package com.example.mobile.core

import com.example.mobile.core.dto.UpdateTodo
import com.example.mobile.core.models.Todo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface TodoApi {

    @GET("/todo")
    fun getTodos(): Call<List<Todo>>


    @GET("/todo/{id}")
    fun getTodoById(@Path("id") id : Int): Call<Todo>

    @POST("/todo/add")
    fun createTodo(): Call<Todo>

    @DELETE("/todo/{id}")
    fun deleteTodo(@Path("id") id : Int): Call<Void>


    @PATCH("/todo/update/{id}")
    fun updateTodo(@Path("id") id : Int,@Body updateTodoDto: UpdateTodo): Call<Void>


}