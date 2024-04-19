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

class FavoriteAdapter : RecyclerView.Adapter<FavoriteViewHolder>() {
    private val listData = ArrayList<Favorite>()
    fun setListFavorite(listData: List<Favorite>) {
        val diffCallback = FavoriteDiffCallback(this.listData, listData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listData.clear()
        this.listData.addAll(listData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listData[position])
    }

}