package com.lisapriliant.appgithubuser.data.retrofit

import com.lisapriliant.appgithubuser.data.response.DetailUserResponse
import com.lisapriliant.appgithubuser.data.response.GitHubResponse
import com.lisapriliant.appgithubuser.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getUsers(): Call<List<ItemsItem>>

    @GET("search/users")
    fun searchUsers(
        @Query("q") query: String
    ): Call<GitHubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}