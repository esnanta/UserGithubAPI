package com.esnanta.usergithubapi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.esnanta.usergithubapi.data.response.UserItem

class UserViewModel: ViewModel() {
    private val users: MutableLiveData<List<UserItem>> by lazy {
        MutableLiveData<List<UserItem>>().also {
            loadUsers()
        }
    }

    fun getUsers(): LiveData<List<UserItem>> {
        return users
    }

    private fun loadUsers() {
        // Do an asynchronous operation to fetch users.
    }
}