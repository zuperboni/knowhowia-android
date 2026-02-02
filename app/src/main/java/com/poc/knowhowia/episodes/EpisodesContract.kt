package com.poc.knowhowia.episodes

import com.poc.knowhowia.model.CharacterUi

interface EpisodesContract {

    interface View {
        fun showLoading(show: Boolean)
        fun showCharacters(items: List<CharacterUi>)
        fun showError(message: String)
    }

    interface Presenter {
        fun attach(view: View)
        fun detach()
        fun loadCharacters()
    }
}