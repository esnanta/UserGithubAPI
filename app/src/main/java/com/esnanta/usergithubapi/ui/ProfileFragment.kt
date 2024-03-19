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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding  = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        if(index==1){
            arguments?.getString(ARG_LOGIN_USER)?.let { loadViewModelFollower(it) }
        }else{
            arguments?.getString(ARG_LOGIN_USER)?.let { loadViewModelFollowing(it) }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setLayoutManager(layoutManager)
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.recyclerView.addItemDecoration(itemDecoration)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding  = null
    }
    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_LOGIN_USER = "login_user"
    }
}