package com.example.mycalck.data

import com.example.mycalck.character.CharacterResponse
import retrofit2.http.GET

interface RickAndMortyApiService {
    @GET("character")
    suspend fun getCharacters(): CharacterResponse
}
