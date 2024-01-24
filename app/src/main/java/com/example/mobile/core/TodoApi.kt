package com.example.mobile.core

import com.example.mobile.core.dto.CreateTodoDto
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

    @GET("/v2/todo")
    fun getTodos(): Call<List<Todo>>

    @GET("/v2/todo/{id}")
    fun getTodoById(@Path("id") id : Int): Call<Todo>

    @POST("/v2/todo/add")
    fun createTodo(@Body todo: CreateTodoDto): Call<Todo>

    @DELETE("/v2/todo/delete/{id}")
    fun deleteTodo(@Path("id") id : Int): Call<Void>

    @PATCH("/v2/todo/update/{id}")
    fun updateTodo(@Path("id") id : Int,@Body updateTodoDto: UpdateTodo): Call<Void>

}