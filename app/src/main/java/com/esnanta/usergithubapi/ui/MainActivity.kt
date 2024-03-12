package com.esnanta.usergithubapi.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.esnanta.usergithubapi.R
import com.esnanta.usergithubapi.data.model.UserAdapter
import com.esnanta.usergithubapi.data.model.UserViewModel
import com.esnanta.usergithubapi.data.response.UserItem
import com.esnanta.usergithubapi.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter

    private val mainViewModel: UserViewModel by viewModels()

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadViewModel();

        val layoutManager = LinearLayoutManager(this)
        binding.userRecyclerView.setLayoutManager(layoutManager)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.userRecyclerView.addItemDecoration(itemDecoration)

        binding.searchView.visibility = View.GONE

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search_menu -> {
                    binding.searchView.visibility = View.VISIBLE
                    binding.searchView.setQuery("sidiqpermana22",false)

                    with(binding) {
                        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                            override fun onQueryTextSubmit(query: String?): Boolean {
                                if (query != null) {
                                    mainViewModel.findUser(query)
                                }
                                binding.searchView.visibility = View.GONE
                                return false
                            }

                            override fun onQueryTextChange(newText: String?): Boolean {
                                //filterList(newText)
                                return false
                            }
                        })
                    }
                    true
                }
                R.id.refresh_menu -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun loadViewModel(){

        mainViewModel.listUser.observe(this) { listUserItem ->
            listUserItem?.let {
                adapter = UserAdapter(listUserItem)
                adapter.updateList(it)

                binding.userRecyclerView.adapter = adapter
                binding.userRecyclerView.layoutManager = LinearLayoutManager(this)
                binding.userRecyclerView.setHasFixedSize(true)
            }
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
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}


