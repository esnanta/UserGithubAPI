package com.esnanta.usergithubapi.data.model

import com.esnanta.usergithubapi.data.response.UserItemResponse

interface IUserItemClickListener {
    fun onUserItemClick(userItem: UserItemResponse)
}