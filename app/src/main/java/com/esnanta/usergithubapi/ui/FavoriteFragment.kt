package com.esnanta.usergithubapi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.esnanta.usergithubapi.databinding.FragmentFavoriteBinding
import com.esnanta.usergithubapi.helper.ViewModelFactory
import com.esnanta.usergithubapi.model.favorite.FavoriteAdapter
import com.esnanta.usergithubapi.model.favorite.FavoriteListViewModel
import com.esnanta.usergithubapi.data.Result

class FavoriteFragment : Fragment() {
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

        val activity = requireActivity() as AppCompatActivity
        favoriteListViewModel = obtainViewModel(activity)

        favoriteListViewModel.getAllFavorites().observe(viewLifecycleOwner) { dataList ->
            if (dataList != null) {
                adapter = FavoriteAdapter()
                adapter.setListFavorite(dataList)
                binding.recyclerView.adapter = adapter
            }
        }



        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setLayoutManager(layoutManager)
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.recyclerView.addItemDecoration(itemDecoration)

//        binding?.fabAdd?.setOnClickListener {
//            val intent = Intent(this@MainActivity, NoteAddUpdateActivity::class.java)
//            startActivity(intent)
//        }

    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteListViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteListViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}