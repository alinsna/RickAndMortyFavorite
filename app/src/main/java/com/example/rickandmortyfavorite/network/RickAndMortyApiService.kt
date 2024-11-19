package com.example.rickandmortyfavorite.network

import com.example.rickandmortyfavorite.model.CharacterResponse
import retrofit2.Call
import retrofit2.http.GET

interface RickAndMortyApiService {
    @GET("character")
    fun getCharacters(): Call<CharacterResponse>
}
