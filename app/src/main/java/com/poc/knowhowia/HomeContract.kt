package com.poc.knowhowia

import com.poc.knowhowia.model.CharacterUi

interface HomeContract {

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
