package com.esnanta.usergithubapi.model.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.esnanta.usergithubapi.R
import com.esnanta.usergithubapi.data.room.Favorite
import com.esnanta.usergithubapi.databinding.ItemFavoriteBinding

class FavoriteAdapter(private val onFavoriteClick: (Favorite) -> Unit) :
    ListAdapter<Favorite, FavoriteViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = getItem(position)
        holder.bind(favorite)

        //val ivFavorite = holder.binding.ivFavorite

//        ivFavorite.setOnClickListener {
//            onFavoriteClick(favorite)
//        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Favorite> =
            object : DiffUtil.ItemCallback<Favorite>() {
                override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                    return oldItem.username == newItem.username
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                    return oldItem == newItem
                }
            }
    }
}