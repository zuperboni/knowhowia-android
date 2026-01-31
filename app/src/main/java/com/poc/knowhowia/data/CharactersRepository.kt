package com.poc.knowhowia.data

import com.poc.knowhowia.remote.RickAndMortyApi
import com.poc.knowhowia.remote.CharactersResponseDto
import io.reactivex.rxjava3.core.Single

class CharactersRepository(
    private val api: RickAndMortyApi
) {
    fun getFirstPage(): Single<CharactersResponseDto> = api.getCharacters(page = 1)
}
