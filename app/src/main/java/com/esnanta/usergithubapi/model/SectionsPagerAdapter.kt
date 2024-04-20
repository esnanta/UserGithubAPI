package com.esnanta.usergithubapi.model

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.esnanta.usergithubapi.ui.FavoriteDetailFragment
import com.esnanta.usergithubapi.ui.ProfileFragment

class SectionsPagerAdapter(activity: AppCompatActivity,
                           private val fragments: List<Fragment>,
                           private var loginUser: String)
    : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        val fragment = fragments[position]
        if (fragment is ProfileFragment) {
            return ProfileFragment.newInstance(loginUser, position + 1)
        } else {
            return FavoriteDetailFragment.newInstance(loginUser, position + 1)
        }
    }
}