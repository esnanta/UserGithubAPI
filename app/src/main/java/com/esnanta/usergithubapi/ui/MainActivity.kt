package com.esnanta.usergithubapi.ui

import android.R
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esnanta.usergithubapi.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewUserModel : ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Set up search function

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
    }
}