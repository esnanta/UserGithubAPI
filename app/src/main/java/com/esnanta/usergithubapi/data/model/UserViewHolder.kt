package com.esnanta.usergithubapi.data.model

import androidx.recyclerview.widget.RecyclerView
import com.esnanta.usergithubapi.data.response.UserItemResponse
import com.esnanta.usergithubapi.databinding.ItemUserBinding

class UserViewHolder(val binding: ItemUserBinding)
    : RecyclerView.ViewHolder(binding.root){
    fun bind(dataItem: UserItemResponse) {
        binding.dataItem = dataItem
    }
}