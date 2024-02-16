package com.esnanta.usergithubapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.SearchView
import com.esnanta.usergithubapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.topAppBar.setOnMenuItemClickListener {menuItem ->
//            when (menuItem.itemId){
//                R.id.search -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.fragment_container, SearchFragment())
//                        .addToBackStack(null)
//                        .commit()
//                    true
//                }
//                else -> false
//            }
//        }

        setSupportActionBar(binding.topAppBar)

        // Set up search function
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Handle search query submission
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Handle search query changes
                return true
            }
        })
    }

}