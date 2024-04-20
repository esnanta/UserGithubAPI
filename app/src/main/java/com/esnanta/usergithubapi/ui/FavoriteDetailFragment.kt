package com.esnanta.usergithubapi.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.esnanta.usergithubapi.R
import com.esnanta.usergithubapi.databinding.FragmentFavoriteBinding
import com.esnanta.usergithubapi.databinding.FragmentFavoriteDetailBinding
import com.esnanta.usergithubapi.helper.ViewModelFactory
import com.esnanta.usergithubapi.model.SectionsPagerAdapter
import com.esnanta.usergithubapi.model.favorite.FavoriteDetailViewModel
import com.esnanta.usergithubapi.model.favorite.FavoriteListViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteDetailFragment : Fragment() {

    private lateinit var mFavoriteDetailViewModel: FavoriteDetailViewModel
    private var _binding: FragmentFavoriteDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding  = FragmentFavoriteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arguments = requireArguments()
        val username = arguments.getString(ARG_USERNAME)
        showUsernameToast(username)

        val activity = requireActivity() as AppCompatActivity
        mFavoriteDetailViewModel = obtainViewModel(activity)

        val sectionsPagerAdapter = username?.let {
            SectionsPagerAdapter(activity,FavoriteDetailFragment::class.java, it)
        }
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        activity.supportActionBar?.elevation = 0f

    }
    private fun showUsernameToast(username: String?) {
        if (username != null) {
            val toastMessage = "Username: $username"
            Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteDetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteDetailViewModel::class.java]
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_USERNAME = "username"

        fun newInstance(username: String, sectionNumber: Int): FavoriteDetailFragment {
            val fragment = FavoriteDetailFragment()
            val args = Bundle().apply {
                putString(ARG_USERNAME, username)
                putInt(ARG_SECTION_NUMBER, sectionNumber)
            }
            fragment.arguments = args
            return fragment
        }

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_follower,
            R.string.tab_following
        )
    }
}