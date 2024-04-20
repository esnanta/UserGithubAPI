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
import com.esnanta.usergithubapi.databinding.ActivityFavoriteDetailBinding
import com.esnanta.usergithubapi.helper.ViewModelFactory
import com.esnanta.usergithubapi.model.SectionsPagerAdapter
import com.esnanta.usergithubapi.model.favorite.FavoriteDetailViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteDetailActivity : AppCompatActivity() {
    private lateinit var mFavoriteDetailViewModel: FavoriteDetailViewModel
    private lateinit var binding : ActivityFavoriteDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mFavoriteDetailViewModel = obtainViewModelFactory(this@FavoriteDetailActivity)
        val username = intent.getStringExtra("EXTRA_USERNAME")

        if (username != null) {
            loadViewModel(username)
        }

        val sectionsPagerAdapter = username?.let {
            val fragmentList = listOf(
                FavoriteDetailFragment.newInstance(username, 1),
                FavoriteDetailFragment.newInstance(username, 2)
            )

            SectionsPagerAdapter(this,fragmentList, it)
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

        mFavoriteDetailViewModel.findUser(loginUser)

        mFavoriteDetailViewModel.userResponse.observe(this) { user ->
            binding.profileName.text = user.name ?: "-NULL-"
            binding.profileLogin.text = "(" + user.login + ")"
            binding.profileFollower.text = resources.getString(R.string.followers) + " " + user.followers!!
            binding.profileFollowing.text = resources.getString(R.string.following) + " " + user.following!!

            Glide.with(this)
                .load(user.avatarUrl)
                .into(binding.profileImage)

            user.login?.let { mFavoriteDetailViewModel.getIsFavorite(it) }

        }

        mFavoriteDetailViewModel.isFavoriteExisted.observe(this) {
            updateFabButton(it)
        }

        mFavoriteDetailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mFavoriteDetailViewModel.snackBarText.observe(this){
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView, snackBarText, Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun updateFabButton(isFavorite: Boolean) {
        val fabFavorites = binding.fabFavorites
        val favorite = Favorite()
        favorite.let { favorite ->
            favorite?.username = mFavoriteDetailViewModel.userResponse.value?.login.toString()
            favorite?.avatarUrl = mFavoriteDetailViewModel.userResponse.value?.avatarUrl
        }

        if (isFavorite) {
            fabFavorites.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.baseline_favorite_24))
            fabFavorites.setOnClickListener {
                mFavoriteDetailViewModel.deleteFavorites(favorite)
            }
        }
        else{
            fabFavorites.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.baseline_favorite_border_24))
            fabFavorites.setOnClickListener {
                mFavoriteDetailViewModel.addNewFavorites(favorite)
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
    private fun obtainViewModelFactory(activity: AppCompatActivity): FavoriteDetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteDetailViewModel::class.java]
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_follower,
            R.string.tab_following
        )
    }
}