package com.example.githubapi.api

import com.example.githubapi.model.AuthUser
import com.example.githubapi.model.SingleResponseAuth
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthEndPoint {

    //Retrofit OAuth
    @FormUrlEncoded
    @POST("auth/sign-up")
    fun SignUp(@Field("name")name : String,
               @Field("username")username : String,
               @Field("email")email : String,
               @Field("password")password : String
    ): Call<SingleResponseAuth<AuthUser>>

    @FormUrlEncoded
    @POST("auth/sign-in")
    fun SignIn(@Field("username")username: String,
               @Field("password")password: String): Call<SingleResponseAuth<AuthUser>>
}