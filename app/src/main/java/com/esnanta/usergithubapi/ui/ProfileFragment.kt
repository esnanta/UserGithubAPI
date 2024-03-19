package com.esnanta.usergithubapi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.esnanta.usergithubapi.data.model.FollowerAdapter
import com.esnanta.usergithubapi.data.model.FollowerViewModel
import com.esnanta.usergithubapi.data.model.FollowingAdapter
import com.esnanta.usergithubapi.data.model.FollowingViewModel
import com.esnanta.usergithubapi.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    //Only valid between onCreateView and onDestroyView.
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private var _adapterFollower: FollowerAdapter? = null
    private var adapterFollower: FollowerAdapter? = _adapterFollower

    private var _adapterFollowing: FollowingAdapter? = null
    private var adapterFollowing: FollowingAdapter? = _adapterFollowing

    private val viewModelFollower: FollowerViewModel by viewModels()
    private val viewModelFollowing: FollowingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding  = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            arguments?.getString(ARG_LOGIN_USER)?.let { loadViewModelFollower(it) }
            arguments?.getString(ARG_LOGIN_USER)?.let { loadViewModelFollowing(it) }
    }

    private fun loadViewModelFollower(loginUser : String) {

        viewModelFollower.findFollower(loginUser)

        viewModelFollower.listFollower.observe(viewLifecycleOwner) { listFollowerItem ->
            listFollowerItem?.let {
                adapterFollower = FollowerAdapter(listFollowerItem)
                adapterFollower?.updateList(it)

                binding.followerRecyclerView.adapter = adapterFollower
                binding.followerRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.followerRecyclerView.setHasFixedSize(true)

                val layoutManager = LinearLayoutManager(requireContext())
                binding.followerRecyclerView.setLayoutManager(layoutManager)
                val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
                binding.followerRecyclerView.addItemDecoration(itemDecoration)
            }
        }

        viewModelFollower.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun loadViewModelFollowing(loginUser : String) {
        viewModelFollowing.findFollowing(loginUser)

        viewModelFollowing.listFollowing.observe(viewLifecycleOwner) { listFollowingItem ->
            listFollowingItem?.let {
                adapterFollowing = FollowingAdapter(listFollowingItem)
                adapterFollowing?.updateList(it)

                binding.followingRecyclerView.adapter = adapterFollowing
                binding.followingRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.followingRecyclerView.setHasFixedSize(true)

                val layoutManager = LinearLayoutManager(requireContext())
                binding.followingRecyclerView.setLayoutManager(layoutManager)
                val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
                binding.followingRecyclerView.addItemDecoration(itemDecoration)
            }
        }

        viewModelFollowing.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding  = null
        _adapterFollower = null
        _adapterFollowing = null
    }
    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_LOGIN_USER = "login_user"
        fun newInstance(loginUser: String): ProfileFragment {
            val fragment = ProfileFragment()
            val args = Bundle()
            args.putString(ARG_LOGIN_USER, loginUser)
            fragment.arguments = args
            return fragment
        }
    }
}