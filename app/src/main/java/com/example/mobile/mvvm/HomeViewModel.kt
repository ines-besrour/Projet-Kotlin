package com.example.mobile.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobile.core.RetrofitClient
import com.example.mobile.core.models.Todo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class HomeViewModel : ViewModel() {


    private var listTodos = MutableLiveData<List<Todo>>()

    init {
        getTodos()
    }

    private fun getTodos(){
        RetrofitClient.todoService.getTodos().enqueue(object :Callback<List<Todo>>{
            override fun onResponse(call: Call<List<Todo>>, response: Response<List<Todo>>) {
                listTodos.value = response.body()
            }

            override fun onFailure(call: Call<List<Todo>>, t: Throwable) {
                Log.d("Getting Todos Exception",t.message.toString())
            }
        })
    }


    fun observeTodos():LiveData<List<Todo>>{
        return listTodos
    }


}