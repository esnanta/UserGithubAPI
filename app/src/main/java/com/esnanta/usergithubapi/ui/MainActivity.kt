package com.esnanta.usergithubapi.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.esnanta.usergithubapi.R
import com.esnanta.usergithubapi.model.search.SearchAdapter
import com.esnanta.usergithubapi.model.search.IUserItemClickListener
import com.esnanta.usergithubapi.model.search.SearchViewModel
import com.esnanta.usergithubapi.data.response.SearchResponseItem
import com.esnanta.usergithubapi.databinding.ActivityMainBinding
import com.esnanta.usergithubapi.model.setting.SettingViewModel
import com.esnanta.usergithubapi.model.setting.SettingViewModelFactory
import com.esnanta.usergithubapi.utils.SettingPreferences
import com.esnanta.usergithubapi.utils.dataStore
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity(), IUserItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadViewModel();

        val layoutManager = LinearLayoutManager(this)
        binding.userRecyclerView.setLayoutManager(layoutManager)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.userRecyclerView.addItemDecoration(itemDecoration)

        setUpTopAppBar()
        showSearchView(false)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val mainViewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(pref)
        )[SettingViewModel::class.java]

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun loadViewModel() {
        var adapter: SearchAdapter
        viewModel.listUser.observe(this) { listUserItem ->
            listUserItem?.let {
                adapter = SearchAdapter(listUserItem)
                adapter.setOnItemClickListener(this)
                adapter.updateList(it)

                binding.userRecyclerView.adapter = adapter
                binding.userRecyclerView.layoutManager = LinearLayoutManager(this)
                binding.userRecyclerView.setHasFixedSize(true)
            }
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.snackBarText.observe(this){
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView, snackBarText,Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setUpTopAppBar(){
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search_menu -> {
                    showSearchView(true)
                    binding.searchView.setQuery("sidiqpermana22",false)

                    with(binding) {
                        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                            override fun onQueryTextSubmit(query: String?): Boolean {
                                if (query != null) {
                                    viewModel.findUser(query)
                                }
                                showSearchView(false)
                                return false
                            }

                            override fun onQueryTextChange(newText: String?): Boolean {
                                //do nothing
                                return false
                            }
                        })
                    }
                    true
                }
                R.id.refresh_menu -> {
                    showLoading(true)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.favorite_menu -> {
                    showLoading(true)
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.setting_menu -> {
                    showLoading(true)
                    val intent = Intent(this, SwitchActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showSearchView(isShowing: Boolean) {
        if (isShowing) {
            binding.searchView.visibility = View.VISIBLE
        } else {
            binding.searchView.visibility = View.GONE
        }
    }

    override fun onUserItemClick(userItem: SearchResponseItem) {
        var intent = Intent(this, ItemDetailActivity::class.java)
        intent.putExtra("EXTRA_LOGIN_USER", userItem.login)
        startActivity(intent)
    }
}
