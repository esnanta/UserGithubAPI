package com.esnanta.usergithubapi.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.esnanta.usergithubapi.R
import com.esnanta.usergithubapi.data.room.Favorite
import com.esnanta.usergithubapi.model.SectionsPagerAdapter
import com.esnanta.usergithubapi.databinding.ActivityItemDetailBinding
import com.esnanta.usergithubapi.helper.ViewModelFactory
import com.esnanta.usergithubapi.model.user.UserViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class ItemDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemDetailBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel = obtainViewModelFactory(this@ItemDetailActivity)
        val loginUser = intent.getStringExtra("EXTRA_LOGIN_USER")

        if (loginUser != null) {
            loadViewModel(loginUser)
        }

        val sectionsPagerAdapter = loginUser?.let {
            SectionsPagerAdapter(this,ProfileFragment::class.java, it)
        }
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

    }

    private fun loadViewModel(loginUser : String) {

        userViewModel.findUser(loginUser)

        userViewModel.userResponse.observe(this) { user ->
            binding.profileName.text = user.name ?: "-NULL-"
            binding.profileLogin.text = "(" + user.login + ")"
            binding.profileFollower.text = resources.getString(R.string.followers) + " " + user.followers!!
            binding.profileFollowing.text = resources.getString(R.string.following) + " " + user.following!!

            Glide.with(this)
                .load(user.avatarUrl)
                .into(binding.profileImage)

            user.login?.let { userViewModel.getIsFavorite(it) }

        }

        userViewModel.isFavoriteExisted.observe(this) {
            updateFabButton(it)
        }

        userViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        userViewModel.snackBarText.observe(this){
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

    private fun obtainViewModelFactory(activity: AppCompatActivity): UserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[UserViewModel::class.java]
    }

    private fun updateFabButton(isFavorite: Boolean) {
        val fabFavorites = binding.fabFavorites
        val favorite = Favorite()
        favorite.let { favorite ->
            favorite?.username = userViewModel.userResponse.value?.login.toString()
            favorite?.avatarUrl = userViewModel.userResponse.value?.avatarUrl
        }

        if (isFavorite) {
            fabFavorites.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.baseline_favorite_24))
            fabFavorites.setOnClickListener {
                userViewModel.deleteFavorites(favorite)
            }
        }
        else{
            fabFavorites.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.baseline_favorite_border_24))
            fabFavorites.setOnClickListener {
                userViewModel.addNewFavorites(favorite)
            }
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_follower,
            R.string.tab_following
        )
    }
}