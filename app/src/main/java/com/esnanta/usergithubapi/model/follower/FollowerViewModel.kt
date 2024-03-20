package com.esnanta.usergithubapi.model.follower

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esnanta.usergithubapi.data.response.FollowerResponseItem
import com.esnanta.usergithubapi.data.retrofit.ApiConfig
import com.esnanta.usergithubapi.ui.ItemDetailActivity
import com.esnanta.usergithubapi.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel: ViewModel() {

    private val _followerItem = MutableLiveData<FollowerResponseItem>()
    val followerItem: LiveData<FollowerResponseItem> = _followerItem

    private val _listFollower = MutableLiveData<List<FollowerResponseItem>>()
    val listFollower: LiveData<List<FollowerResponseItem>> = _listFollower

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
        client.enqueue(object : Callback <List<FollowerResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowerResponseItem>>,
                response: Response <List<FollowerResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollower.value = response.body()
                    if (_listFollower.value.isNullOrEmpty()) {
                        _snackBarText.value = Event("User not found")
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _snackBarText.value = Event("An error occurred") //
                }
            }
            override fun onFailure(call: Call<List<FollowerResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}


