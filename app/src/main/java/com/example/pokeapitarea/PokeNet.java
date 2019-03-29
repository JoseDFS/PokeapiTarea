package com.example.pokeapitarea;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokeNet {
    @GET("pokemon")
    default Call<Respond> obtenerListaPokemon(@Query("limit") int limit, @Query("offset") int offset) {
        return null;
    }
}
