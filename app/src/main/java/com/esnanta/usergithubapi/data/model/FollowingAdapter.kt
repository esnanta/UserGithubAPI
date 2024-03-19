package com.esnanta.usergithubapi.data.model

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esnanta.usergithubapi.data.response.FollowingResponseItem
import com.esnanta.usergithubapi.databinding.ItemFollowingBinding

class FollowingAdapter(private var followingItemList: List<FollowingResponseItem>)
    : RecyclerView.Adapter<FollowingViewHolder>(){

    private lateinit var binding: ItemFollowingBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        binding = ItemFollowingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowingViewHolder(binding);
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        val dataItem = followingItemList[position]
        return holder.bind(dataItem)
    }

    override fun getItemCount(): Int = followingItemList.size
    fun updateList(filterList: List<FollowingResponseItem>) {
        followingItemList = filterList
        notifyDataSetChanged()
    }

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