package com.esnanta.usergithubapi.model.favorite

import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.esnanta.usergithubapi.R
import com.esnanta.usergithubapi.data.room.Favorite
import com.esnanta.usergithubapi.databinding.ItemFavoriteBinding

class FavoriteViewHolder(val binding: ItemFavoriteBinding)
    : RecyclerView.ViewHolder(binding.root) {
    fun bind(favorite: Favorite) {
        binding.tvUsername.text = favorite.username
        Glide.with(itemView.context)
            .load(favorite.avatarUrl)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
            .into(binding.imageViewAvatar)

        //THIS FOR GO TO DETAIL
//        cvItemNote.setOnClickListener {
//            val intent = Intent(it.context, NoteAddUpdateActivity::class.java)
//            intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, note)
//            it.context.startActivity(intent)
//        }
    }
}