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
    private val mainViewModel: UserViewModel by viewModels()  // Using 'by viewModels()' delegate

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        //private const val USERNAME_GITHUB = "sidiqpermana"
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
                    with(binding) {
                        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                            override fun onQueryTextSubmit(query: String?): Boolean {
                                if (query != null) {
                                    mainViewModel.loginUsername = query
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

    private fun filterList(query: String?) {
        val filteredList = if (query.isNullOrBlank()) {
            allUsers  // If the search is empty, show all users
        } else {
            allUsers.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.email.contains(query, ignoreCase = true)
            }
        }
        userAdapter.updateList(filteredList)
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<CourseRVModal> = ArrayList()

        // running a for loop to compare elements.
        for (item in courseList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.courseName.toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            courseRVAdapter.filterList(filteredlist)
        }
    }


    private fun setListUser(listUser: List<UserItem>) {
        binding.userRecyclerView.adapter = UserAdapter(listUser)
        binding.userRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.userRecyclerView.setHasFixedSize(true)
    }

    private fun loadViewModel(){
        mainViewModel.listUser.observe(this) { listUserItem ->
            if (listUserItem != null) {
                setListUser(listUserItem)
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