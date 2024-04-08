package com.esnanta.usergithubapi.data.room

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = FavoriteRoomDatabase.getInstance(application)
        mFavoriteDao = db.favoriteDao()
    }
    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoriteDao.getAllFavorites()
    fun insert(favorite: Favorite) {
        executorService.execute { mFavoriteDao.insert(favorite) }
    }
    fun delete(favorite: Favorite) {
        executorService.execute { mFavoriteDao.delete(favorite) }
    }
    fun isFavoriteExisted(username: String): LiveData<Boolean> {
        val result = mFavoriteDao.isFavoriteExisted(username)
        Log.d(TAG, "isExisted result for username $username: ${result.value}")
        return result
    }
}