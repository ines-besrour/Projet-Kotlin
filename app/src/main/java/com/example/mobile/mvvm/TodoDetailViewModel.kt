package com.example.mobile.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobile.core.RetrofitClient
import com.example.mobile.core.dto.CreateTodoDto
import com.example.mobile.core.dto.UpdateTodo
import com.example.mobile.core.models.Todo
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodoDetailViewModel:ViewModel() {

    private var todo = MutableLiveData<Todo?>()
    private var error = MutableLiveData<String?>()
    private var deletedTodo = MutableLiveData<Boolean>()

    fun getTodoById(id:Int){
        RetrofitClient.todoService.getTodoById(id)
            .enqueue(object : Callback<Todo> {
                override fun onResponse(call: Call<Todo>, response: Response<Todo>) {
                    if(response.isSuccessful){
                        Log.d("GET TODO",response.body().toString())
                        todo.value = response.body()
                        error.value=null
                    }else{
                        val errorBody = response.errorBody()?.string()

                        val gson = Gson()
                        val jsonError = gson.fromJson(errorBody, JsonObject::class.java)
                        getTodoError(jsonError.get("message").asString)
                    }

                }

                override fun onFailure(call: Call<Todo>, t: Throwable) {
                    getTodoError("An Error Occured")
                    Log.d("Add Todo Failed",t.message.toString())
                }
            })
    }

    fun deleteTodoById(id:Int){
        RetrofitClient.todoService.deleteTodo(id)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if(response.isSuccessful){
                        deletedTodo.value = true
                        error.value=null
                    }else{
                        val errorBody = response.errorBody()?.string()
                        val gson = Gson()
                        val jsonError = gson.fromJson(errorBody, JsonObject::class.java)
                        deleteError(jsonError.get("message").asString)
                    }

                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    deleteError("An Error Occured")
                    Log.d("Add Todo Failed",t.message.toString())
                }
            })
    }


    fun getTodoError(msg:String){
        error.value=msg
        todo.value=null
    }


    fun deleteError(msg:String){
        error.value=msg
        deletedTodo.value=false
    }

    fun getError(): LiveData<String?> {
        return error
    }

    fun getTodo(): LiveData<Todo?> {
        return todo
    }

    fun getDeletedTodo(): LiveData<Boolean> {
        return deletedTodo
    }



}