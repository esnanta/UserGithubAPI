package com.esnanta.usergithubapi.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    companion object {
        private const val TAG = "FavoriteDao"
    }
    @Query("SELECT * FROM favorite ORDER BY username DESC")
    fun getAllFavorites(): LiveData<List<Favorite>>

    @Query("SELECT * FROM favorite WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): Favorite

    @Query("SELECT EXISTS(SELECT * FROM favorite WHERE username = :username)")
    fun isFavoriteExisted(username: String): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)
}