package com.example.mobile.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobile.core.RetrofitClient
import com.example.mobile.core.dto.CreateTodoDto
import com.example.mobile.core.dto.UpdateTodo
import com.example.mobile.core.models.Todo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodoDetailViewModel:ViewModel() {

    private var todo = MutableLiveData<Todo?>()
    private var error = MutableLiveData<String?>()
    private var deletedTodo = MutableLiveData<Void?>()

    fun getTodoById(id:Int){
        RetrofitClient.todoService.getTodoById(id)
            .enqueue(object : Callback<Todo> {
                override fun onResponse(call: Call<Todo>, response: Response<Todo>) {
                    if(response.isSuccessful){
                        todo.value = response.body()
                        error.value=null
                    }else{
                        getTodoError(response.errorBody().toString())
                    }

                }

                override fun onFailure(call: Call<Todo>, t: Throwable) {
                    getTodoError(t.message.toString())
                    Log.d("Add Todo Failed",t.message.toString())
                }
            })
    }

    fun deleteTodoById(id:Int){
        RetrofitClient.todoService.deleteTodo(id)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if(response.isSuccessful){
                        deletedTodo.value = response.body()
                        error.value=null
                    }else{
                        deleteError(response.errorBody().toString())
                    }

                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    deleteError(t.message.toString())
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
        deletedTodo.value=null
    }

    fun getError(): LiveData<String?> {
        return error
    }

    fun getTodo(): LiveData<Todo?> {
        return todo
    }

    fun getDeletedTodo(): LiveData<Void?> {
        return deletedTodo
    }



}