package com.esnanta.usergithubapi.data.retrofit

import com.esnanta.usergithubapi.data.response.FollowerResponse
import com.esnanta.usergithubapi.data.response.FollowingResponse
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

    @GET("users/{login}")
    fun getDetail(@Path("login") login: String): Call<UserResponse>

    @GET("users/{login}/followers")
    fun getListFollower(@Path("login") login: String): Call<FollowerResponse>

    @GET("users/{login}/following")
    fun getListFollowing(@Path("login") login: String): Call<FollowingResponse>
}
