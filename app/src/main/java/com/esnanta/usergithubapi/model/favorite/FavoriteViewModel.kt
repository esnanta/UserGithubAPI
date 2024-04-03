package com.esnanta.usergithubapi.model.favorite

import androidx.lifecycle.ViewModel

class FavoriteViewModel(private val repository: FavoriteRepository) : ViewModel() {
    fun getFavorites() = repository.getFavorites()
}