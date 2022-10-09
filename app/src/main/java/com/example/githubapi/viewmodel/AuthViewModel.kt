package com.example.githubapi.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.githubapi.api.ApiService
import com.example.githubapi.model.AuthUser
import com.example.githubapi.model.SingleResponseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel(application: Application):AndroidViewModel(application) {

    val signUp : MutableLiveData<AuthUser?> = MutableLiveData()
    val signIn : MutableLiveData<AuthUser?> = MutableLiveData()

    fun doSignUp(name:String,username :String,email:String,password:String){
        ApiService.ApiAuth().SignUp(name,username,email,password)
            .enqueue(object : Callback<SingleResponseAuth<AuthUser>>{
                override fun onResponse(
                    call: Call<SingleResponseAuth<AuthUser>>,
                    response: Response<SingleResponseAuth<AuthUser>>
                ) {
                   if(response.isSuccessful){
                       val body = response.body()
                       if(body != null){
                           signUp.postValue(body.data)
                       }
                   }
                }

                override fun onFailure(call: Call<SingleResponseAuth<AuthUser>>, t: Throwable) {
                    Log.d("ResponseError","${t.message}")
                }

            })
    }

    fun doSignIn(username :String,password :String){
        ApiService.ApiAuth().SignIn(username,password)
            .enqueue(object : Callback<SingleResponseAuth<AuthUser>>{
                override fun onResponse(
                    call: Call<SingleResponseAuth<AuthUser>>,
                    response: Response<SingleResponseAuth<AuthUser>>
                ) {
                   if(response.isSuccessful){
                       val body = response.body()
                       if(body != null){
                           signIn.postValue(body.data)
                       }
                   }
                }

                override fun onFailure(call: Call<SingleResponseAuth<AuthUser>>, t: Throwable) {
                    Log.d("ResponseError","${t.message}")
                }

            })
    }

}