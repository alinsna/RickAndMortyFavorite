package com.example.rickandmortyfavorite.repository

import com.example.rickandmortyfavorite.database.FavoriteDao
import com.example.rickandmortyfavorite.model.FavoriteCharacter

class FavoriteRepository(private val dao: FavoriteDao) {

    val allFavorites = dao.getAllFavorites()

    suspend fun addFavorite(character: FavoriteCharacter) {
        dao.insertFavorite(character)
    }

    suspend fun removeFavorite(character: FavoriteCharacter): Int {
        return dao.deleteFavorite(character)
    }
}
