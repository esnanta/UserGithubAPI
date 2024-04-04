package com.esnanta.usergithubapi.model.favorite

import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.esnanta.usergithubapi.R
import com.esnanta.usergithubapi.data.room.Favorite
import com.esnanta.usergithubapi.databinding.ItemFavoriteBinding

class FavoriteViewHolder(val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(
    binding.root
) {
    fun bind(favorite: Favorite) {
        binding.tvUsername.text = favorite.username
        Glide.with(itemView.context)
            .load(favorite.avatarUrl)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
            .into(binding.imageViewAvatar)
        itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(favorite.avatarUrl)
            itemView.context.startActivity(intent)
        }
    }
}