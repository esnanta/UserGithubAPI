package com.esnanta.usergithubapi.model.favorite

import androidx.recyclerview.widget.DiffUtil
import com.esnanta.usergithubapi.data.room.Favorite

class FavoriteDiffCallback(private val oldList: List<Favorite>, private val newList: List<Favorite>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].username == newList[newItemPosition].username
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.username == newItem.username && oldItem.avatarUrl == newItem.avatarUrl
    }
}