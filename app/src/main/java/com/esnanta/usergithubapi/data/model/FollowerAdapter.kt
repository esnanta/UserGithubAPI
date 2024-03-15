package com.esnanta.usergithubapi.data.model

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esnanta.usergithubapi.data.response.FollowerItemResponse
import com.esnanta.usergithubapi.databinding.FragmentProfileBinding

class FollowerAdapter(private var followerItemList: List<FollowerItemResponse>)
    : RecyclerView.Adapter<FollowerViewHolder>(){

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        binding = FragmentProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowerViewHolder(binding);
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        val dataItem = followerItemList[position]
        return holder.bind(dataItem)
    }

    override fun getItemCount(): Int = followerItemList.size

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