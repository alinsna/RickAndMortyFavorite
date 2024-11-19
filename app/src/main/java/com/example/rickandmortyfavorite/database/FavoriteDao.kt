package com.example.rickandmortyfavorite.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.rickandmortyfavorite.model.FavoriteCharacter

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(character: FavoriteCharacter): Long

    @Delete
    fun deleteFavorite(character: FavoriteCharacter): Int

    @Query("SELECT * FROM favorite_characters")
    fun getAllFavorites(): LiveData<List<FavoriteCharacter>>
}
