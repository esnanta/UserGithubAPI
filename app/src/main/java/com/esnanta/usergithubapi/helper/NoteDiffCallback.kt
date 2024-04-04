package com.esnanta.usergithubapi.helper

import androidx.recyclerview.widget.DiffUtil
import com.esnanta.usergithubapi.data.room.Favorite

class NoteDiffCallback(
    private val oldFavoriteList: List<Favorite>,
    private val newFavoriteList: List<Favorite>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavoriteList.size
    override fun getNewListSize(): Int = newFavoriteList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavoriteList[oldItemPosition].username == newFavoriteList[newItemPosition].username
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavorite = oldFavoriteList[oldItemPosition]
        val newFavorite = newFavoriteList[newItemPosition]
        return oldFavorite.username == newFavorite.username &&
                oldFavorite.avatarUrl == oldFavorite.avatarUrl
    }
}