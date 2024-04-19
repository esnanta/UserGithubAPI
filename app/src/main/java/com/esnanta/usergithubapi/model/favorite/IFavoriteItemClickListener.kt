package com.esnanta.usergithubapi.model.favorite

import com.esnanta.usergithubapi.data.room.Favorite

interface IFavoriteItemClickListener {
    fun onFavoriteItemClick(favorite: Favorite)
}