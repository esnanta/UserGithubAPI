package com.esnanta.usergithubapi.model

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.esnanta.usergithubapi.ui.FavoriteDetailFragment
import com.esnanta.usergithubapi.ui.ProfileFragment

class SectionsPagerAdapter(activity: AppCompatActivity,
                           private val fragmentClass: Class<out Fragment>,
                           private var loginUser: String)
    : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = fragmentClass.getDeclaredConstructor().newInstance()
        if (fragment is ProfileFragment) {
            return ProfileFragment.newInstance(loginUser, position + 1)
        } else {
            return fragment
        }
    }

}