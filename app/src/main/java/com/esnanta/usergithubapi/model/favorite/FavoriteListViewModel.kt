package com.esnanta.usergithubapi.model.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.esnanta.usergithubapi.data.room.Favorite
import com.esnanta.usergithubapi.data.room.FavoriteRepository

class FavoriteListViewModel (application: Application) : ViewModel() {
    private val mRepository: FavoriteRepository = FavoriteRepository(application)
    fun getAllFavorites(): LiveData<List<Favorite>> = mRepository.getAllFavorites()
}