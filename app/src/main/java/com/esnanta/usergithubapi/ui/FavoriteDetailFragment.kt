package com.esnanta.usergithubapi.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.esnanta.usergithubapi.databinding.FragmentFavoriteDetailBinding
import com.esnanta.usergithubapi.model.follower.FollowerAdapter
import com.esnanta.usergithubapi.model.follower.FollowerViewModel
import com.esnanta.usergithubapi.model.following.FollowingAdapter
import com.esnanta.usergithubapi.model.following.FollowingViewModel

class FavoriteDetailFragment : Fragment() {

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

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        if(index==1){
            arguments?.getString(ARG_USERNAME)?.let { loadViewModelFollower(it) }
        }else{
            arguments?.getString(ARG_USERNAME)?.let { loadViewModelFollowing(it) }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setLayoutManager(layoutManager)
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.recyclerView.addItemDecoration(itemDecoration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding  = null
    }

    private fun loadViewModelFollower(loginUser : String) {

        val viewModel : FollowerViewModel by viewModels()
        var adapterFollower: FollowerAdapter?

        viewModel.findFollower(loginUser)

        viewModel.listFollower.observe(viewLifecycleOwner) { listFollowerItem ->
            listFollowerItem?.let {
                adapterFollower = FollowerAdapter(listFollowerItem)
                adapterFollower?.updateList(it)
                binding.recyclerView.adapter = adapterFollower
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun loadViewModelFollowing(loginUser : String) {
        val viewModel : FollowingViewModel by viewModels()
        var adapterFollowing: FollowingAdapter?

        viewModel.findFollowing(loginUser)

        viewModel.listFollowing.observe(viewLifecycleOwner) { listFollowingItem ->
            listFollowingItem?.let {
                adapterFollowing = FollowingAdapter(listFollowingItem)
                adapterFollowing?.updateList(it)
                binding.recyclerView.adapter = adapterFollowing
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
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
    }
}