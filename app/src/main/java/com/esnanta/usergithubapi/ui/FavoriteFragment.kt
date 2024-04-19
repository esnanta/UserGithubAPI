package com.esnanta.usergithubapi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.esnanta.usergithubapi.R
import com.esnanta.usergithubapi.data.room.Favorite
import com.esnanta.usergithubapi.databinding.FragmentFavoriteBinding
import com.esnanta.usergithubapi.helper.ViewModelFactory
import com.esnanta.usergithubapi.model.favorite.FavoriteAdapter
import com.esnanta.usergithubapi.model.favorite.FavoriteListViewModel
import com.esnanta.usergithubapi.model.favorite.IFavoriteItemClickListener

class FavoriteFragment : Fragment(), IFavoriteItemClickListener {
    private lateinit var favoriteListViewModel: FavoriteListViewModel
    private lateinit var adapter: FavoriteAdapter

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding  = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setLayoutManager(layoutManager)
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.recyclerView.addItemDecoration(itemDecoration)

        val activity = requireActivity() as AppCompatActivity
        favoriteListViewModel = obtainViewModel(activity)

        favoriteListViewModel.getAllFavorites()?.observe(viewLifecycleOwner) { dataList ->
            if (dataList != null) {
                showLoading(true)
                adapter = FavoriteAdapter(favoriteListViewModel)
                adapter.setListFavorite(dataList)
                adapter.setOnItemClickListener(this)
                binding.recyclerView.adapter = adapter
                showLoading(false)
            }
        }

        favoriteListViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteListViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteListViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onFavoriteItemClick(favorite: Favorite) {
        val username = favorite.username
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val favoriteDetailFragment = FavoriteDetailFragment.newInstance(username)

        fragmentTransaction.replace(R.id.fragment_container, favoriteDetailFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarFavorite.visibility = View.VISIBLE
        } else {
            binding.progressBarFavorite.visibility = View.GONE
        }
    }
}