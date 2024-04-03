package com.esnanta.usergithubapi.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.esnanta.usergithubapi.model.favorite.Favorite

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite ORDER BY username DESC")
    fun getAllFavorites(): LiveData<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(news: List<Favorite>)

    @Query("DELETE FROM favorite WHERE username = :username")
    suspend fun deleteFavorite(username: String):Int
    
}