package com.esnanta.usergithubapi.data.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esnanta.usergithubapi.data.response.UserItemResponse
import com.esnanta.usergithubapi.data.response.UserResponse
import com.esnanta.usergithubapi.data.retrofit.ApiConfig
import com.esnanta.usergithubapi.ui.MainActivity
import com.esnanta.usergithubapi.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel: ViewModel() {

    private val _userItem = MutableLiveData<UserItemResponse>()
    val userItem: LiveData<UserItemResponse> = _userItem

    private val _listUser = MutableLiveData<List<UserItemResponse>?>()
    val listUser: MutableLiveData<List<UserItemResponse>?> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = _snackBarText

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private const val LOGIN_USER = "sidiqpermana"
    }

    init{
        findUser(LOGIN_USER)
    }

    fun findUser(searchUser:String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getSearch(searchUser)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.items
                    if (_listUser.value.isNullOrEmpty()) {
                        _snackBarText.value = Event("User not found")
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _snackBarText.value = Event("An error occurred") //
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}