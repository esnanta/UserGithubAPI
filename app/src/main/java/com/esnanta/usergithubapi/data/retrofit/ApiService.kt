package com.esnanta.usergithubapi.data.retrofit

import com.esnanta.usergithubapi.BuildConfig
import com.esnanta.usergithubapi.data.response.FollowerResponseItem
import com.esnanta.usergithubapi.data.response.FollowingResponseItem
import com.esnanta.usergithubapi.data.response.SearchResponse
import com.esnanta.usergithubapi.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.TOKEN_GITHUB}")
    fun getSearch(@Query("q") query: String): Call<SearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.TOKEN_GITHUB}")
    fun getDetail(@Path("username") username: String): Call<UserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.TOKEN_GITHUB}")
    fun getListFollower(@Path("username") username: String): Call<List<FollowerResponseItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.TOKEN_GITHUB}")
    fun getListFollowing(@Path("username") username: String): Call<List<FollowingResponseItem>>
}
