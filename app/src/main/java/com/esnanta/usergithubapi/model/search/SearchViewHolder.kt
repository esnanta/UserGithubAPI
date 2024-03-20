package com.esnanta.usergithubapi.model.search

import androidx.recyclerview.widget.RecyclerView
import com.esnanta.usergithubapi.data.response.SearchResponseItem
import com.esnanta.usergithubapi.databinding.SearchUserBinding

class SearchViewHolder(val binding: SearchUserBinding)
    : RecyclerView.ViewHolder(binding.root){
    fun bind(dataItem: SearchResponseItem) {
        binding.dataItem = dataItem
    }
}