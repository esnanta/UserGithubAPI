package com.esnanta.usergithubapi.di

import android.content.Context
import com.esnanta.usergithubapi.model.favorite.FavoriteRepository
import com.esnanta.usergithubapi.data.room.FavoriteDatabase
import com.esnanta.usergithubapi.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavoriteRepository {
        val database = FavoriteDatabase.getInstance(context)
        val dao = database.favoriteDao()
        val appExecutors = AppExecutors()
        return FavoriteRepository.getInstance(dao, appExecutors)
    }
}