package com.example.githubapi.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiService {
    companion object{
        private var retrofit: Retrofit? = null
        private var okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        fun getClient(): Retrofit {
            return if (retrofit == null){
                retrofit = Retrofit.Builder().baseUrl(Constant.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create()).build()
                retrofit!!
            }else{
                retrofit!!
            }
        }
        fun getClientAuth(): Retrofit {
            return if (retrofit == null){
                retrofit = Retrofit.Builder().baseUrl(Constant.BASE_URL_AUTH)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create()).build()
                retrofit!!
            }else{
                retrofit!!
            }
        }
        fun ApiEndPoint() : ApiEndPoint = getClient().create(ApiEndPoint::class.java)
        fun ApiAuth():AuthEndPoint = getClientAuth().create(AuthEndPoint::class.java)
    }
}

class Constant{
    companion object{
        const val BASE_URL = "https://api.github.com/"
        const val BASE_URL_AUTH = "https://apibarang.herokuapp.com/"
    }
}