package com.example.mobile.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobile.core.RetrofitClient
import com.example.mobile.core.SharedPreferencesManager
import com.example.mobile.core.dto.CreateTodoDto
import com.example.mobile.core.dto.UpdateTodo
import com.example.mobile.core.models.Todo
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodosViewModel: ViewModel() {

    private var addedTodo = MutableLiveData<Todo?>()
    private var error = MutableLiveData<String?>()
    private var updateTodo = MutableLiveData<Boolean>()


    fun addTodo(name:String,description : String){
        RetrofitClient.todoService.createTodo(CreateTodoDto(name, description))
            .enqueue(object : Callback<Todo> {
                override fun onResponse(call: Call<Todo>, response: Response<Todo>) {
                    if(response.isSuccessful){
                        error.value=null
                        addedTodo.value = response.body()
                    }else{
                        val errorBody = response.errorBody()?.string()

                        val gson = Gson()
                        val jsonError = gson.fromJson(errorBody, JsonObject::class.java)
                        addTodoError(jsonError.get("message").asString)
                    }

                }

                override fun onFailure(call: Call<Todo>, t: Throwable) {
                    addTodoError("An error Occured")
                    Log.d("Add Todo Failed",t.message.toString())
                }
            })
    }

    fun updateTodo(id:Int,name:String?,description : String?,status:String?){
        RetrofitClient.todoService.updateTodo(id, UpdateTodo(name,description,status))
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if(response.isSuccessful){
                        error.value=null
                        updateTodo.value = true
                    }else{
                        val errorBody = response.errorBody()?.string()
                        val gson = Gson()
                        val jsonError = gson.fromJson(errorBody, JsonObject::class.java)
                        updateError(jsonError.get("message").asString)
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    addTodoError("An error Occured")
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
        updateTodo.value=false
    }

    fun getError():LiveData<String?>{
        return error
    }

    fun getAddedTodo():LiveData<Todo?>{
        return addedTodo
    }

    fun getUpdatedTodo():LiveData<Boolean>{
        return updateTodo
    }

}