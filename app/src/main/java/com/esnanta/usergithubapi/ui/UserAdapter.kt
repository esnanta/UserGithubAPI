package com.esnanta.usergithubapi.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.esnanta.usergithubapi.data.response.UsersItem
import com.esnanta.usergithubapi.databinding.ListUserBinding

class UserAdapter: ListAdapter<UsersItem, UserAdapter.UserViewHolder> (DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding);
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }

    class UserViewHolder (val binding: ListUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(review: UsersItem){
            binding.tvIdUser.text = "${review.id}"
            binding.tvUsername.text = "${review.login}"
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UsersItem>() {
            override fun areItemsTheSame(oldItem: UsersItem, newItem: UsersItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: UsersItem, newItem: UsersItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}