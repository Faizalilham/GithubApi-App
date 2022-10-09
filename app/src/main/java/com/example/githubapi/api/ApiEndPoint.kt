package com.example.githubapi.api

import com.example.githubapi.model.ListResponse
import com.example.githubapi.model.SingleResponse
import com.example.githubapi.model.UserGithub
import com.example.githubapi.model.UserGithubDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiEndPoint {

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getDataUser(@Query("q")query : String):Call<ListResponse<UserGithub>>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(@Path("username")username:String):Call<UserGithubDetail>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowersUser(@Path("username")username :String):Call<ArrayList<UserGithub>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowingUser(@Path("username")username :String):Call<ArrayList<UserGithub>>
}