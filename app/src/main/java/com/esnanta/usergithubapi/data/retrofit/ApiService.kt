package com.esnanta.usergithubapi.data.retrofit

import com.esnanta.usergithubapi.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

class ApiService {
    interface ApiService {
        @GET("search/users?q={username}")
        fun getSearch(
            @Path("login") login: String
        ): Call<UserResponse>

        @GET("users/{username}")
        fun getDetail(
            @Path("login") login: String
        ): Call<UserResponse>

        @GET("users/{username}/followers")
        fun getListFollower(
            @Path("login") login: String
        ): Call<UserResponse>

        @GET("users/{username}/following")
        fun getListFollowing(
            @Path("login") login: String
        ): Call<UserResponse>
    }
}