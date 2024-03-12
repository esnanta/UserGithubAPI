package com.esnanta.usergithubapi.data.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.esnanta.usergithubapi.data.response.UserItem
import com.esnanta.usergithubapi.data.response.UserResponse
import com.esnanta.usergithubapi.data.retrofit.ApiConfig
import com.esnanta.usergithubapi.ui.MainActivity
import com.esnanta.usergithubapi.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel: ViewModel() {

    private val _userItem = MutableLiveData<UserItem>()
    val userItem: LiveData<UserItem> = _userItem

    private val _listUser = MutableLiveData<List<UserItem>?>()
    val listUser: MutableLiveData<List<UserItem>?> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    var loginUsername : String = "sidiqpermana"

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private const val USERNAME_GITHUB = "sidiqpermana"
    }

    init{
        findUser(loginUsername)
    }

    private fun findUser(searchUserName:String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getSearch(searchUserName)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}