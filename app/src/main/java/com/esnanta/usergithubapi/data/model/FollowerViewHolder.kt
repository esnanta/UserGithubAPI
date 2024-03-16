package com.esnanta.usergithubapi.data.model

import androidx.recyclerview.widget.RecyclerView
import com.esnanta.usergithubapi.data.response.FollowerItemResponse
import com.esnanta.usergithubapi.databinding.ItemFollowerBinding

class FollowerViewHolder(val binding: ItemFollowerBinding)
    : RecyclerView.ViewHolder(binding.root){
    fun bind(dataItem: FollowerItemResponse) {
        binding.dataItem = dataItem
    }
}