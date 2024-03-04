package com.esnanta.usergithubapi.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.esnanta.usergithubapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewUserModel : ViewModel by viewModels()

    companion object {
        private const val TAG = "MainActivity"
        private const val USER_GITHUB_ID = "xxxxx"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userItem = DummyData.getDummyData(this)
        val userAdapter = UserAdapter(userItem)

        val recyclerViewNews = binding.userRecyclerView
        recyclerViewNews.adapter = userAdapter
        recyclerViewNews.layoutManager = LinearLayoutManager(this)
        recyclerViewNews.setHasFixedSize(true)

    }
}