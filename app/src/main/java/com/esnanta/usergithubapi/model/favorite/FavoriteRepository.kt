package com.esnanta.usergithubapi.model.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.esnanta.usergithubapi.data.Result
import com.esnanta.usergithubapi.data.room.FavoriteDao
import com.esnanta.usergithubapi.utils.AppExecutors

class FavoriteRepository private constructor(
    private val favoriteDao: FavoriteDao,
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Result<List<Favorite>>>()

    fun getFavorites(): LiveData<Result<List<Favorite>>> {
        result.value = Result.Loading

        val localData = favoriteDao.getAllFavorites()
        result.addSource(localData) { newData: List<Favorite> ->
            result.value = Result.Success(newData)
        }
        return result
    }

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            favoriteDao: FavoriteDao,
            appExecutors: AppExecutors
        ): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(favoriteDao, appExecutors)
            }.also { instance = it }
    }
}