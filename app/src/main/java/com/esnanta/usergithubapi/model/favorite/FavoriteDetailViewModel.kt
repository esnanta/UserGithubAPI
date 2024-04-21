package com.esnanta.usergithubapi.model.favorite

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esnanta.usergithubapi.data.response.UserResponse
import com.esnanta.usergithubapi.data.retrofit.ApiConfig
import com.esnanta.usergithubapi.data.room.Favorite
import com.esnanta.usergithubapi.data.room.FavoriteRepository
import com.esnanta.usergithubapi.ui.FavoriteDetailFragment
import com.esnanta.usergithubapi.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteDetailViewModel(application: Application) : ViewModel() {
    private val mRepository: FavoriteRepository = FavoriteRepository(application)

    private val _userResponse = MutableLiveData<UserResponse>()
    val userResponse: LiveData<UserResponse> = _userResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = _snackBarText

    private val _isFavoriteExisted = MutableLiveData(false)
    val isFavoriteExisted: LiveData<Boolean> = _isFavoriteExisted

    private val _favorite = MutableLiveData<Favorite>()
    val favorite: LiveData<Favorite> = _favorite

    fun findUser(searchUser:String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getDetail(searchUser)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userResponse.value = response.body()

                    if (_userResponse.value==null) {
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

    fun addNewFavorites(favorite:Favorite){
        mRepository.insert(favorite)
        _isFavoriteExisted.value = true
    }

    fun deleteFavorites(favorite:Favorite){
        mRepository.delete(favorite)
        _isFavoriteExisted.value = false
    }

    fun getIsFavorite(username:String){
        _isFavoriteExisted.value = mRepository.isFavoriteExisted(username)
    }

    fun getFavoriteUserByUsername(username: String) {
        _favorite.value = mRepository.getFavoriteUserByUsername(username)
    }

    companion object {
        private val TAG = FavoriteDetailFragment::class.java.simpleName
    }
}