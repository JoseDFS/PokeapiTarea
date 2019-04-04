package com.example.pokeapitarea

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeNet {
    @GET("pokemon")
    fun obtenerListaPokemon(@Query("limit") limit: Int, @Query("offset") offset: Int): Call<Respond>? {
        return null
    }
}
