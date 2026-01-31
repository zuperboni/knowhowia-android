package com.poc.knowhowia.di

import com.poc.knowhowia.CharactersFragment
import com.poc.knowhowia.details.CharacterDetailsFragment
import com.poc.knowhowia.HomeContract
import com.poc.knowhowia.HomePresenter
import com.poc.knowhowia.data.CharactersRepository
import com.poc.knowhowia.details.CharacterDetailsContract
import com.poc.knowhowia.details.CharacterDetailsPresenter
import com.poc.knowhowia.details.CharacterDetailsRepository
import com.poc.knowhowia.remote.NetworkModule
import com.poc.knowhowia.remote.RickAndMortyApi
import org.koin.dsl.module

private val coreModule = module {
    // Aqui eu reaproveito seu NetworkModule atual (object)
    // Se preferir, dá pra montar Retrofit aqui dentro do Koin (mais puro).
    single<RickAndMortyApi> { NetworkModule.api }
}

private val charactersFragmentModule = module {
    scope<CharactersFragment> {
        scoped { CharactersRepository(get()) }
        scoped<HomeContract.Presenter> { HomePresenter(get()) }
    }
}

private val detailsFragmentModule = module {
    scope<CharacterDetailsFragment> {
        scoped { CharacterDetailsRepository(get()) }
        scoped<CharacterDetailsContract.Presenter> { CharacterDetailsPresenter(get()) }
    }
}

val appModules = listOf(
    coreModule,
    charactersFragmentModule,
    detailsFragmentModule
)
