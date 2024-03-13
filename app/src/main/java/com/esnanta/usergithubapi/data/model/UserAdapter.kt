package com.esnanta.usergithubapi.data.model

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esnanta.usergithubapi.data.response.UserItem
import com.esnanta.usergithubapi.databinding.ItemUserBinding

class UserAdapter(private var userItemList: List<UserItem>)
    : RecyclerView.Adapter<UserViewHolder>(){

    private lateinit var binding: ItemUserBinding
    private lateinit var clickListener: IUserItemClickListener

    fun setOnItemClickListener(listener: IUserItemClickListener) {
        clickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding);
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val userItem = userItemList[position]
        holder.binding.root.setOnClickListener {
            clickListener.onUserItemClick(userItem)
        }
        return holder.bind(userItem)
    }

    override fun getItemCount(): Int = userItemList.size

    fun updateList(filterList: List<UserItem>) {
        userItemList = filterList
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