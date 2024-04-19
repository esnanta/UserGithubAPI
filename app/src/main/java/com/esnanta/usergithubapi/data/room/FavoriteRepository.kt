package com.esnanta.usergithubapi.data.room

import android.app.Application
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getInstance(application)
        mFavoriteDao = db.favoriteDao()
    }
    fun getAllFavorites() = mFavoriteDao.getAllFavorites()
    fun insert(favorite: Favorite) {
        executorService.execute { mFavoriteDao.insert(favorite) }
    }
    fun delete(favorite: Favorite) {
        executorService.execute { mFavoriteDao.delete(favorite) }
    }

    fun isFavoriteExisted(username: String): Boolean {
        val isFavorite = executorService.submit(Callable {
            mFavoriteDao.isFavoriteExisted(username)
        })

        return try {
            isFavorite.get()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}