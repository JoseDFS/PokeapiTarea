package com.example.pokeapitarea


import com.example.pokeapitarea.R
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.list_element_pokemon.*
import kotlinx.android.synthetic.main.list_element_pokemon.view.*
import java.util.*


class PokemonAdapter(private val context: Context) :
    RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    private val dataset: ArrayList<Pokemon>


    init {
        dataset = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_element_pokemon, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p = dataset[position]
        holder.nombreTextView.text = p.name

        var aux:Int= position+1

        Glide.with(holder.itemView.context)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$aux.png")
            .into(holder.itemView.fotoPokemon)


    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun addPokemon(listaPokemon: ArrayList<Pokemon>) {
        dataset.addAll(listaPokemon)
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val fotoImageView: ImageView
        val nombreTextView: TextView

        init {

            fotoImageView = itemView.fotoPokemon
            nombreTextView = itemView.nombrePokemon
        }


    }
}