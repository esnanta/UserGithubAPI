package com.esnanta.usergithubapi.model.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.esnanta.usergithubapi.data.room.Favorite
import com.esnanta.usergithubapi.databinding.ItemFavoriteBinding

class FavoriteAdapter(
    private val favoriteListViewModel: FavoriteListViewModel)
        : RecyclerView.Adapter<FavoriteViewHolder>() {

    private val listData = ArrayList<Favorite>()
    private lateinit var clickListener: IFavoriteItemClickListener

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
        val favorite : Favorite = favoriteListViewModel.getFavoriteUserByUsername(listData[position].username)
        holder.binding.root.setOnClickListener {
            clickListener.onFavoriteItemClick(favorite)
        }
        return holder.bind(favorite)
    }

    fun setOnItemClickListener(listener: IFavoriteItemClickListener) {
        clickListener = listener
    }
}