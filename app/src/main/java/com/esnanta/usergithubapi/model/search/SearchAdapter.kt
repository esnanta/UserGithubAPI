package com.esnanta.usergithubapi.model.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esnanta.usergithubapi.data.response.SearchResponseItem
import com.esnanta.usergithubapi.databinding.SearchUserBinding

class SearchAdapter(private var searchUserList: List<SearchResponseItem>)
    : RecyclerView.Adapter<SearchViewHolder>(){

    private lateinit var binding: SearchUserBinding
    private lateinit var clickListener: IUserItemClickListener

    fun setOnItemClickListener(listener: IUserItemClickListener) {
        clickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        binding = SearchUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding);
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val searchUser = searchUserList[position]
        holder.binding.root.setOnClickListener {
            clickListener.onUserItemClick(searchUser)
        }
        return holder.bind(searchUser)
    }

    override fun getItemCount(): Int = searchUserList.size

    fun updateList(filterList: List<SearchResponseItem>) {
        searchUserList = filterList
        notifyDataSetChanged()
    }
}