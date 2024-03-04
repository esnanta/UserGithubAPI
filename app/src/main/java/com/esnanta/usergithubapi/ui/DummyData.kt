package com.esnanta.usergithubapi.ui

import android.content.Context
import com.esnanta.usergithubapi.data.response.UserItem


object DummyData {
    fun getDummyData(context: Context): List<UserItem>{
        val userItem = arrayListOf<UserItem>()

        val userItem1 = UserItem(
            1,
            "test1"
        )
        val userItem2 = UserItem(
            2,
            "test2"
        )
        val userItem3 = UserItem(
            3,
            "test3"
        )


        userItem.add(userItem1)
        userItem.add(userItem2)
        userItem.add(userItem3)

        return userItem

    }
}