package com.example.pokeapitarea


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private var retrofit: Retrofit? = null


    private var recyclerView: RecyclerView? = null
    private var listaPokemonAdapter: PokemonAdapter? = null

    private var offset: Int = 0

    private var cargar: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView = findViewById<View>(R.id.rv_pokemon_list) as RecyclerView
        listaPokemonAdapter = PokemonAdapter(this)

        recyclerView!!.adapter = listaPokemonAdapter
        recyclerView!!.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                    if (cargar) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            Log.i(TAG, " Llegamos al final.")

                            cargar = false
                            offset += 20
                            getData(offset)
                        }
                    }
                }
            }
        })


        retrofit = Retrofit.Builder()
            .baseUrl("http://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        cargar = true
        offset = 0
        getData(offset)
    }

    private fun getData(offset: Int) {
        val service = retrofit!!.create(PokeNet::class.java)
        val pokemonRespuestaCall = service.obtenerListaPokemon(20, offset)

        pokemonRespuestaCall!!.enqueue(object : Callback<Respond> {
            override fun onResponse(call: Call<Respond>, response: Response<Respond>) {
                cargar = true
                if (response.isSuccessful) {

                    val pokemonRespuesta = response.body()
                    val listaPokemon = pokemonRespuesta!!.results

                    listaPokemonAdapter!!.addPokemon(listaPokemon!!)

                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody()!!)
                }
            }

            override fun onFailure(call: Call<Respond>, t: Throwable) {
                cargar = true
                Log.e(TAG, " onFailure: " + t.message)
            }
        })
    }

    companion object {

        private val TAG = "POKEDEX"
    }
}