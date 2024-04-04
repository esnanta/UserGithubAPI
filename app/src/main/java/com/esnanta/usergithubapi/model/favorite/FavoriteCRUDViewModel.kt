package com.esnanta.usergithubapi.model.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import com.esnanta.usergithubapi.data.room.Favorite
import com.esnanta.usergithubapi.data.room.FavoriteRepository

class FavoriteCRUDViewModel(application: Application): ViewModel() {
    private val mRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(note: Favorite) {
        mRepository.insert(note)
    }

    fun delete(note: Favorite) {
        mRepository.delete(note)
    }
}