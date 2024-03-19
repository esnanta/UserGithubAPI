package com.esnanta.usergithubapi.data.model

import androidx.recyclerview.widget.RecyclerView
import com.esnanta.usergithubapi.data.response.FollowingResponseItem
import com.esnanta.usergithubapi.databinding.ItemFollowingBinding

class FollowingViewHolder(val binding: ItemFollowingBinding)
    : RecyclerView.ViewHolder(binding.root){
    fun bind(dataItem: FollowingResponseItem) {
        binding.dataItem = dataItem
    }
}