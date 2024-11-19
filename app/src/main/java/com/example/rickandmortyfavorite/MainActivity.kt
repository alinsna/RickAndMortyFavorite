package com.example.rickandmortyfavorite

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyfavorite.model.Character
import com.example.rickandmortyfavorite.model.CharacterResponse
import com.example.rickandmortyfavorite.network.RetrofitInstance
import com.example.rickandmortyfavorite.viewmodel.FavoriteViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var characterAdapter: CharacterAdapter
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonFavorites = findViewById<Button>(R.id.buttonFavorites)
        buttonFavorites.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchCharacters()
    }

    private fun fetchCharacters() {
        RetrofitInstance.api.getCharacters().enqueue(object : Callback<CharacterResponse> {
            override fun onResponse(call: Call<CharacterResponse>, response: Response<CharacterResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { characterResponse ->
                        val mutableCharacters = characterResponse.results.toMutableList()

                        favoriteViewModel.allFavorites.observe(this@MainActivity) { favorites ->
                            val favoriteIds = favorites.map { it.id }

                            mutableCharacters.forEach { character ->
                                character.isFavorite = favoriteIds.contains(character.id)
                            }
                            
                            characterAdapter = CharacterAdapter(mutableCharacters) { character ->
                                if (character.isFavorite) {
                                    favoriteViewModel.addFavorite(character)
                                    Toast.makeText(this@MainActivity, "${character.name} added to favorites!", Toast.LENGTH_SHORT).show()
                                } else {
                                    favoriteViewModel.removeFavorite(character)
                                    Toast.makeText(this@MainActivity, "${character.name} removed from favorites!", Toast.LENGTH_SHORT).show()
                                }
                            }
                            recyclerView.adapter = characterAdapter
                        }
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Failed to load data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
