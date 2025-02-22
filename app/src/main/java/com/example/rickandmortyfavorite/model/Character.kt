package com.example.rickandmortyfavorite.model

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String,
    var isFavorite: Boolean = false
)
