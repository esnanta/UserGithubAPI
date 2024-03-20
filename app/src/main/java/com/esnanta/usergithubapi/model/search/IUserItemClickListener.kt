package com.esnanta.usergithubapi.model.search

import com.esnanta.usergithubapi.data.response.SearchResponseItem

interface IUserItemClickListener {
    fun onUserItemClick(userItem: SearchResponseItem)
}