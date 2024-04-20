package com.esnanta.usergithubapi.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.esnanta.usergithubapi.data.room.Favorite
import com.esnanta.usergithubapi.databinding.ActivityFavoriteBinding
import com.esnanta.usergithubapi.helper.ViewModelFactory
import com.esnanta.usergithubapi.model.favorite.FavoriteAdapter
import com.esnanta.usergithubapi.model.favorite.FavoriteListViewModel
import com.esnanta.usergithubapi.model.favorite.IFavoriteItemClickListener

class FavoriteActivity : AppCompatActivity(), IFavoriteItemClickListener {
    private lateinit var favoriteListViewModel: FavoriteListViewModel
    private lateinit var adapter: FavoriteAdapter
    private lateinit var binding : ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setLayoutManager(layoutManager)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.recyclerView.addItemDecoration(itemDecoration)

        favoriteListViewModel = obtainViewModel(this)

        favoriteListViewModel.getAllFavorites()?.observe(this) { dataList ->
            if (dataList != null) {
                showLoading(true)
                adapter = FavoriteAdapter(favoriteListViewModel)
                adapter.setListFavorite(dataList)
                adapter.setOnItemClickListener(this)
                binding.recyclerView.adapter = adapter
                showLoading(false)
            }
        }

        favoriteListViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    override fun onFavoriteItemClick(favorite: Favorite) {
        val username = favorite.username
        var intent = Intent(this, FavoriteDetailActivity::class.java)
        intent.putExtra("EXTRA_USERNAME", username)
        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteListViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteListViewModel::class.java]
    }
}