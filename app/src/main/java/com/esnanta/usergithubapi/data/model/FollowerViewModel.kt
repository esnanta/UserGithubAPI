package com.esnanta.usergithubapi.data.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esnanta.usergithubapi.data.response.FollowerItemResponse
import com.esnanta.usergithubapi.data.response.FollowerResponse
import com.esnanta.usergithubapi.data.retrofit.ApiConfig
import com.esnanta.usergithubapi.ui.ItemDetailActivity
import com.esnanta.usergithubapi.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel: ViewModel() {

    private val _followerItem = MutableLiveData<FollowerItemResponse>()
    val followerItem: LiveData<FollowerItemResponse> = _followerItem

    private val _listFollower = MutableLiveData<List<FollowerItemResponse>?>()
    val listFollower: MutableLiveData<List<FollowerItemResponse>?> = _listFollower

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = _snackBarText

    companion object {
        private val TAG = ItemDetailActivity::class.java.simpleName
        private const val LOGIN_USER = "sidiqpermana"
    }

    init{
        findFollower(LOGIN_USER)
    }

    fun findFollower(searchUser:String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListFollower(searchUser)
        client.enqueue(object : Callback<FollowerResponse> {
            override fun onResponse(
                call: Call<FollowerResponse>,
                response: Response<FollowerResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollower.value = response.body()?.followerList
                    if (_listFollower.value.isNullOrEmpty()) {
                        _snackBarText.value = Event("User not found")
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _snackBarText.value = Event("An error occurred") //
                }
            }
            override fun onFailure(call: Call<FollowerResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}