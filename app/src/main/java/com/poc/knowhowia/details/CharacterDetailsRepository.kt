package com.poc.knowhowia.details

import com.poc.knowhowia.remote.RickAndMortyApi

class CharacterDetailsRepository(
    private val api: RickAndMortyApi
) {
    fun getCharacter(id: Int) = api.getCharacter(id)
}
