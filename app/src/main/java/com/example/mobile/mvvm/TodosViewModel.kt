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

class TodosViewModel: ViewModel() {

    private var addedTodo = MutableLiveData<Todo?>()
    private var error = MutableLiveData<String?>()
    private var updateTodo = MutableLiveData<Void?>()


    fun addTodo(name:String,description : String){
        RetrofitClient.todoService.createTodo(CreateTodoDto(name, description))
            .enqueue(object : Callback<Todo> {
                override fun onResponse(call: Call<Todo>, response: Response<Todo>) {
                    if(response.isSuccessful){
                        addedTodo.value = response.body()
                        error.value=null
                    }else{
                        addTodoError(response.errorBody().toString())
                    }

                }

                override fun onFailure(call: Call<Todo>, t: Throwable) {
                    addTodoError(t.message.toString())
                    Log.d("Add Todo Failed",t.message.toString())
                }
            })
    }

    fun updateTodo(id:Int,name:String?,description : String?,status:String?){
        RetrofitClient.todoService.updateTodo(id, UpdateTodo(name,description,status))
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if(response.isSuccessful){
                        updateTodo.value = response.body()
                        error.value=null
                    }else{
                        updateError(response.errorBody().toString())
                    }

                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    updateError(t.message.toString())
                    Log.d("Add Todo Failed",t.message.toString())
                }
            })
    }


    fun addTodoError(msg:String){
        error.value=msg
        addedTodo.value=null
    }


    fun updateError(msg:String){
        error.value=msg
        updateTodo.value=null
    }

    fun getError():LiveData<String?>{
        return error
    }

    fun getAddedTodo():LiveData<Todo?>{
        return addedTodo
    }

    fun getUpdatedTodo():LiveData<Void?>{
        return updateTodo
    }

}