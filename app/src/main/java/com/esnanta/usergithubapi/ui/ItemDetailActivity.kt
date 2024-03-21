package com.esnanta.usergithubapi.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.esnanta.usergithubapi.R
import com.esnanta.usergithubapi.model.SectionsPagerAdapter
import com.esnanta.usergithubapi.databinding.ActivityItemDetailBinding
import com.esnanta.usergithubapi.model.user.UserViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ItemDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemDetailBinding

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_follower,
            R.string.tab_following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loginUser = intent.getStringExtra("EXTRA_LOGIN_USER")
        if (loginUser != null) {
            loadViewModel(loginUser)
        }

        val sectionsPagerAdapter = loginUser?.let { SectionsPagerAdapter(this, it) }
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

    }

    private fun loadViewModel(loginUser : String) {
        val viewModel: UserViewModel by viewModels()

        viewModel.findUser(loginUser)

        viewModel.userItem.observe(this) { user ->
            binding.profileName.text = user.name ?: "-NULL-"
            binding.profileLogin.text = "(" + user.login + ")"
            binding.profileFollower.text = resources.getString(R.string.followers) + " " + user.followers!!
            binding.profileFollowing.text = resources.getString(R.string.following) + " " + user.following!!

            Glide.with(this)
                .load(user.avatarUrl)
                .into(binding.profileImage)
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

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}