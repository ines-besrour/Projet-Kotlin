package com.example.mobile.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobile.core.RetrofitClient
import com.example.mobile.core.dto.AuthenticateTodo
import com.example.mobile.core.dto.LoginDto
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel : ViewModel() {

    private var authDetails = MutableLiveData<LoginDto?>()
    private var error = MutableLiveData<String?>()
    private var isSignedUpSuccessfully =MutableLiveData<Boolean>()

    fun login(email: String,password : String){
        RetrofitClient.authService.login(AuthenticateTodo(email,password)).enqueue(object :
            Callback<LoginDto> {
            override fun onResponse(call: Call<LoginDto>, response: Response<LoginDto>) {
                if(response.isSuccessful){
                    authDetails.value = response.body()
                    error.value=null
                }else{
                    val errorBody = response.errorBody()?.string()

                    val gson = Gson()
                    val jsonError = gson.fromJson(errorBody, JsonObject::class.java)
                    loginError(jsonError.get("message").asString)
                }
            }
            override fun onFailure(call: Call<LoginDto>, t: Throwable) {
                loginError("An Error Occured")
                Log.d("Login Problem",t.message.toString())
            }
        })
    }

    fun signUp(email: String,password : String){
        RetrofitClient.authService.signUp(AuthenticateTodo(email,password)).enqueue(object :
            Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    isSignedUpSuccessfully.value=true
                    error.value=null
                }else{
                    val errorBody = response.errorBody()?.string()

                    val gson = Gson()
                    val jsonError = gson.fromJson(errorBody, JsonObject::class.java)
                    signUpError(jsonError.get("message").asString)
                }

            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                signUpError("An Error Occured")
                Log.d("Sign Up Problem",t.message.toString())
            }
        })
    }

    private fun loginError(msg:String){
        error.value=msg
        authDetails.value=null
    }

    private fun signUpError(msg:String){
        error.value=msg
        isSignedUpSuccessfully.value=false
    }

    fun getAuthDetails() : LiveData<LoginDto?>{
        return authDetails
    }

    fun getIsSignedSuccessfully() : LiveData<Boolean>{
        return isSignedUpSuccessfully
    }

    fun getError() : LiveData<String?>{
        return error
    }
}