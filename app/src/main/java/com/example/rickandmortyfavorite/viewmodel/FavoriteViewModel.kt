package com.example.rickandmortyfavorite.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyfavorite.database.AppDatabase
import com.example.rickandmortyfavorite.model.Character
import com.example.rickandmortyfavorite.model.FavoriteCharacter
import com.example.rickandmortyfavorite.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FavoriteRepository
    val allFavoritesFromDB: LiveData<List<FavoriteCharacter>>

    private val _allFavorites = MutableLiveData<List<Character>>()
    val allFavorites: LiveData<List<Character>> get() = _allFavorites

    init {
        val dao = AppDatabase.getDatabase(application).favoriteDao()
        repository = FavoriteRepository(dao)
        allFavoritesFromDB = repository.allFavorites

        allFavoritesFromDB.observeForever { favoriteList ->
            _allFavorites.postValue(favoriteList.map {
                Character(
                    it.id,
                    it.name,
                    it.status,
                    it.species,
                    it.gender,
                    it.image,
                    true
                )
            })
        }
    }

    fun addFavorite(character: Character) {
        val favoriteCharacter = FavoriteCharacter(
            id = character.id,
            name = character.name,
            status = character.status,
            species = character.species,
            gender = character.gender,
            image = character.image
        )

        viewModelScope.launch(Dispatchers.IO) {
            repository.addFavorite(favoriteCharacter)
        }
    }

    fun removeFavorite(character: Character) {
        val favoriteCharacter = FavoriteCharacter(
            id = character.id,
            name = character.name,
            status = character.status,
            species = character.species,
            gender = character.gender,
            image = character.image
        )

        viewModelScope.launch(Dispatchers.IO) {
            repository.removeFavorite(favoriteCharacter)
            _allFavorites.postValue(_allFavorites.value?.filter { it.id != character.id })
        }
    }
}
