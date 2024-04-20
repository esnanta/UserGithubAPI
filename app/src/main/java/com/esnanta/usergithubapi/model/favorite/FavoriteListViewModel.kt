package com.esnanta.usergithubapi.model.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esnanta.usergithubapi.data.room.Favorite
import com.esnanta.usergithubapi.data.room.FavoriteRepository

class FavoriteListViewModel (application: Application) : ViewModel() {
    private val mRepository: FavoriteRepository = FavoriteRepository(application)

    private var _listFavorite = MutableLiveData<List<Favorite>>()
    var listFavorite: LiveData<List<Favorite>>? = _listFavorite

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllFavorites() {
        listFavorite = mRepository.getAllFavorites()
    }

    fun getFavoriteUserByUsername(username: String): Favorite {
        return mRepository.getFavoriteUserByUsername(username)
    }
}