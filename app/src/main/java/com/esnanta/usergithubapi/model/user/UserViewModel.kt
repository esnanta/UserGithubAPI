package com.esnanta.usergithubapi.model.user

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esnanta.usergithubapi.data.response.UserResponse
import com.esnanta.usergithubapi.data.retrofit.ApiConfig
import com.esnanta.usergithubapi.data.room.Favorite
import com.esnanta.usergithubapi.data.room.FavoriteRepository
import com.esnanta.usergithubapi.ui.ItemDetailActivity
import com.esnanta.usergithubapi.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel (application: Application): ViewModel() {

    private val mRepository: FavoriteRepository = FavoriteRepository(application)

    private val _userItem = MutableLiveData<UserResponse>()
    val userItem: LiveData<UserResponse> = _userItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = _snackBarText

    companion object {
        private val TAG = ItemDetailActivity::class.java.simpleName
    }

    fun findUser(searchUser:String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetail(searchUser)
        client.enqueue(object : Callback <UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response <UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userItem.value = response.body()
                    if (_userItem.value==null) {
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

    fun addToFavorites(favorite:Favorite){
        mRepository.insert(favorite)
    }
}