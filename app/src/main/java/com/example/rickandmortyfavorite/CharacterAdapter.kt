package com.example.rickandmortyfavorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmortyfavorite.model.Character

class CharacterAdapter(
    private val characters: MutableList<Character>,
    private val onFavoriteClick: (Character) -> Unit
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.bind(character)

        holder.imageViewFavorite.setColorFilter(
            if (character.isFavorite)
                ContextCompat.getColor(holder.itemView.context, R.color.favorite_active)
            else
                ContextCompat.getColor(holder.itemView.context, R.color.favorite_inactive)
        )

        holder.imageViewFavorite.setOnClickListener {
            character.isFavorite = !character.isFavorite
            onFavoriteClick(character)
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = characters.size

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        private val textViewStatus: TextView = itemView.findViewById(R.id.textViewStatus)
        private val textViewSpecies: TextView = itemView.findViewById(R.id.textViewSpecies)
        private val textViewGender: TextView = itemView.findViewById(R.id.textViewGender)
        val imageViewFavorite: ImageView = itemView.findViewById(R.id.favoriteIcon)

        fun bind(character: Character) {
            textViewName.text = character.name
            textViewStatus.text = character.status
            textViewSpecies.text = character.species
            textViewGender.text = character.gender
            Glide.with(itemView.context).load(character.image).into(imageView)
        }
    }
}
