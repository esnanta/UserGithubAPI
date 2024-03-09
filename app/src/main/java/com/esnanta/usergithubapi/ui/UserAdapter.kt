package com.esnanta.usergithubapi.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esnanta.usergithubapi.data.response.UserItem
import com.esnanta.usergithubapi.databinding.ItemUserBinding

class UserAdapter(private val userItemList: List<UserItem>)
    : RecyclerView.Adapter<UserViewHolder>(){

    private lateinit var binding: ItemUserBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding);
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val userItem = userItemList[position]
        return holder.bind(userItem)
    }

    override fun getItemCount(): Int = userItemList.size

    companion object BindingAdapters {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(imageView: ImageView, url: String?) {
            if (!url.isNullOrBlank()) {
                Glide.with(imageView.context)
                    .load(url)
                    .into(imageView)
            }
        }
    }
}