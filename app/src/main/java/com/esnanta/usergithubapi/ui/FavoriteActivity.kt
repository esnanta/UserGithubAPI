package com.esnanta.usergithubapi.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.esnanta.usergithubapi.R
import com.esnanta.usergithubapi.databinding.ActivityFavoriteBinding
class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val favoriteFragment = FavoriteFragment()
        fragmentTransaction.add(binding.fragmentContainer.id, favoriteFragment)
        fragmentTransaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
}