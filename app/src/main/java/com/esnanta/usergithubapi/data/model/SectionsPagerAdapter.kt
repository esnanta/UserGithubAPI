package com.esnanta.usergithubapi.data.model

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.esnanta.usergithubapi.ui.ProfileFragment

class SectionsPagerAdapter(
    activity: AppCompatActivity, private var loginUser: String)
    : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = ProfileFragment()
        fragment.arguments = Bundle().apply {
            putInt(ProfileFragment.ARG_SECTION_NUMBER, position + 1)
            putString(ProfileFragment.ARG_LOGIN_USER, loginUser)
        }
        return fragment
    }
}