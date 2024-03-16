package com.esnanta.usergithubapi.ui

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.esnanta.usergithubapi.R
import com.esnanta.usergithubapi.data.model.SectionsPagerAdapter
import com.esnanta.usergithubapi.databinding.ActivityItemDetailBinding
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


        val sectionsPagerAdapter = loginUser?.let { SectionsPagerAdapter(this, it) }
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        if (savedInstanceState == null) {
            val profileFragment =
                loginUser?.let { ProfileFragment.newInstance(it) } // Create instance

            if (profileFragment != null) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, profileFragment)
                    .commit()
            }
        }
    }
}