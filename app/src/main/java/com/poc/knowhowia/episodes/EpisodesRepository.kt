package com.poc.knowhowia.episodes

import com.poc.knowhowia.remote.RickAndMortyApi
import com.poc.knowhowia.remote.CharactersResponseDto
import io.reactivex.rxjava3.core.Single

class EpisodesRepository(
    private val api: RickAndMortyApi
) {
    fun getFirstPage(): Single<CharactersResponseDto> = api.getEpisodes(page = 1)
}
