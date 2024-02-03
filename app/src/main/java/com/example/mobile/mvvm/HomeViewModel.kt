package com.example.mobile.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobile.core.RetrofitClient
import com.example.mobile.core.models.Todo
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class HomeViewModel : ViewModel() {


    private var listTodos = MutableLiveData<List<Todo>?>()
    private var error = MutableLiveData<String?>()



    fun getTodos(){
        RetrofitClient.todoService.getTodos().enqueue(object :Callback<List<Todo>>{
            override fun onResponse(call: Call<List<Todo>>, response: Response<List<Todo>>) {
                if(response.isSuccessful){
                    listTodos.value = response.body()
                    error.value=null
                }else{
                    val errorBody = response.errorBody()?.string()

                    val gson = Gson()
                    val jsonError = gson.fromJson(errorBody, JsonObject::class.java)
                    listTodos.value = null
                    error.value=jsonError.get("message").asString
                }

            }

            override fun onFailure(call: Call<List<Todo>>, t: Throwable) {
                listTodos.value = null
                error.value="An Error Occured"
                Log.d("Getting Todos Exception",t.message.toString())
            }
        })
    }


    fun observeTodos():LiveData<List<Todo>?>{
        return listTodos
    }


}