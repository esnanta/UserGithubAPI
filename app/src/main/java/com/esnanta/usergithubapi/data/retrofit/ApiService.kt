package com.esnanta.usergithubapi.data.retrofit

import com.esnanta.usergithubapi.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: token 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")

    @GET("search/users")
    fun getSearch(@Query("q") query: String): Call<UserResponse>

    @GET("users/{username}")
    fun getDetail(@Path("username") login: String): Call<UserResponse>

    @GET("users/{username}/followers")
    fun getListFollower(@Path("username") login: String): Call<UserResponse>

    @GET("users/{username}/following")
    fun getListFollowing(@Path("username") login: String): Call<UserResponse>
}
