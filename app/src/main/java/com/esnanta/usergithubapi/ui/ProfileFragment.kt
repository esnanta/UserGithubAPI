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
import com.esnanta.usergithubapi.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    //Only valid between onCreateView and onDestroyView.
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private var _adapter: FollowerAdapter? = null
    private var adapter: FollowerAdapter? = _adapter

    private val viewModel: FollowerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding  = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString(ARG_LOGIN_USER)?.let { loadViewModel(it) }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.userRecyclerView.setLayoutManager(layoutManager)
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.userRecyclerView.addItemDecoration(itemDecoration)

    }

    private fun loadViewModel(loginUser : String) {

        viewModel.findFollower(loginUser)

        viewModel.listFollower.observe(viewLifecycleOwner) { listFollowerItem ->
            listFollowerItem?.let {
                adapter = FollowerAdapter(listFollowerItem)
                adapter?.updateList(it)

                binding?.userRecyclerView?.adapter = adapter
                binding?.userRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
                binding?.userRecyclerView?.setHasFixedSize(true)
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
        _adapter = null
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