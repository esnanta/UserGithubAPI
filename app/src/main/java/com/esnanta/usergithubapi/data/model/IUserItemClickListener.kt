package com.esnanta.usergithubapi.data.model

import com.esnanta.usergithubapi.data.response.UserItem

interface IUserItemClickListener {
    fun onUserItemClick(userItem: UserItem)
}