package com.esnanta.usergithubapi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.esnanta.usergithubapi.R
import com.esnanta.usergithubapi.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    //Only valid between onCreateView and onDestroyView.
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding  = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvLabel: TextView = binding.sectionLabel
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val loginUser = arguments?.getString(ARG_LOGIN_USER)

        tvLabel.text = getString(R.string.content_tab_text, index) + loginUser
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