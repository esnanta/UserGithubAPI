package com.esnanta.usergithubapi.model.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esnanta.usergithubapi.data.response.SearchResponseItem
import com.esnanta.usergithubapi.data.response.SearchResponse
import com.esnanta.usergithubapi.data.retrofit.ApiConfig
import com.esnanta.usergithubapi.ui.MainActivity
import com.esnanta.usergithubapi.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel: ViewModel() {

    private val _userItem = MutableLiveData<SearchResponseItem>()
    val userItem: LiveData<SearchResponseItem> = _userItem

    private val _listUser = MutableLiveData<List<SearchResponseItem>?>()
    val listUser: MutableLiveData<List<SearchResponseItem>?> = _listUser

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
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
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
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}