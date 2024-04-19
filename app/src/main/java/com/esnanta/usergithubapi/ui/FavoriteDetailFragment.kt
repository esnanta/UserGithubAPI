package com.esnanta.usergithubapi.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.esnanta.usergithubapi.R
import com.esnanta.usergithubapi.databinding.FragmentFavoriteBinding
import com.esnanta.usergithubapi.databinding.FragmentFavoriteDetailBinding
import com.esnanta.usergithubapi.model.favorite.FavoriteDetailViewModel

class FavoriteDetailFragment : Fragment() {

    private val viewModel: FavoriteDetailViewModel by viewModels()
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
    }
    private fun showUsernameToast(username: String?) {
        if (username != null) {
            val toastMessage = "Username: $username"
            Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show()
        }
    }
    companion object {
        const val ARG_USERNAME = "username"
        fun newInstance(username: String): FavoriteDetailFragment {
            val fragment = FavoriteDetailFragment()
            val args = Bundle().apply {
                putString(ARG_USERNAME, username)
            }
            fragment.arguments = args
            return fragment
        }
    }
}