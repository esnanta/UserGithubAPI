package com.esnanta.usergithubapi.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esnanta.usergithubapi.data.response.UserItem
import com.esnanta.usergithubapi.databinding.ItemUserBinding

class UserViewHolder(private val binding: ItemUserBinding)
    : RecyclerView.ViewHolder(binding.root){
    fun bind(userItem: UserItem) {
        binding.userItem = userItem
    }
}