package com.poc.knowhowia.remote

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character")
    fun getCharacters(
        @Query("page") page: Int = 1
    ): Single<CharactersResponseDto>

    @GET("character/{id}")
    fun getCharacter(@Path("id") id: Int): Single<CharacterDto>
}
