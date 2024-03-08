package com.esnanta.usergithubapi.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.esnanta.usergithubapi.data.response.UserItem
import com.esnanta.usergithubapi.data.response.UserResponse
import com.esnanta.usergithubapi.data.retrofit.ApiService
import com.esnanta.usergithubapi.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewUserModel : ViewModel by viewModels()

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private const val USERNAME_GITHUB = "sidiqpermana"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[UserViewModel::class.java]

        mainViewModel.listUser.observe(this) { listUserItem ->
            setListUser(listUserItem)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.snackbarText.observe(this){
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView, snackBarText,Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.userRecyclerView.setLayoutManager(layoutManager)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.userRecyclerView.addItemDecoration(itemDecoration)


    }

    private fun setListUser(listUser: List<UserItem>) {
        val recyclerViewNews = binding.userRecyclerView
        recyclerViewNews.adapter = UserAdapter(listUser)
        recyclerViewNews.layoutManager = LinearLayoutManager(this)
        recyclerViewNews.setHasFixedSize(true)
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun getDummy(){
        val userItem = DummyData.getDummyData(this)
        val userAdapter = UserAdapter(userItem)

        val recyclerViewNews = binding.userRecyclerView
        recyclerViewNews.adapter = userAdapter
        recyclerViewNews.layoutManager = LinearLayoutManager(this)
        recyclerViewNews.setHasFixedSize(true)
    }
}