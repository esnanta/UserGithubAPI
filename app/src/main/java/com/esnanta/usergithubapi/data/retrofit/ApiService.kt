package com.esnanta.usergithubapi.data.retrofit

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
    @Headers("Authorization: token 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")
    fun getSearch(@Query("q") query: String): Call<SearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetail(@Path("username") username: String): Call<UserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getListFollower(@Path("username") username: String): Call<List<FollowerResponseItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getListFollowing(@Path("username") username: String): Call<List<FollowingResponseItem>>
}
