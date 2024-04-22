package com.esnanta.usergithubapi.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
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

        loadViewModel()
    }

    override fun onStart() {
        super.onStart()
        loadViewModel()
    }

    private fun loadViewModel(){
        favoriteListViewModel = obtainViewModel(this)
        favoriteListViewModel.getAllFavorites()

        favoriteListViewModel.listFavorite?.observe(this) { dataList ->
            if (dataList.isEmpty()) {
                adapter = FavoriteAdapter(favoriteListViewModel)
                binding.recyclerView.adapter = adapter
                val toast = Toast.makeText(this, "Database is empty", Toast.LENGTH_SHORT)
                toast.show()
            }
            else{
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
        val intent = Intent(this, FavoriteDetailActivity::class.java)
        intent.putExtra("EXTRA_USERNAME", username)
        startActivityForResult(intent, REQUEST_DELETE_FAVORITE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_DELETE_FAVORITE && resultCode == Activity.RESULT_OK) {
            loadViewModel()
        }
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

    companion object {
        private const val REQUEST_DELETE_FAVORITE = 1
    }
}