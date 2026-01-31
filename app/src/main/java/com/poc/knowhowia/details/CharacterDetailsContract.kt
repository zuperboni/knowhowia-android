package com.poc.knowhowia.details

interface CharacterDetailsContract {

    interface View {
        fun showLoading(show: Boolean)
        fun showCharacter(data: CharacterDetailsUi)
        fun showError(message: String)
    }

    interface Presenter {
        fun attach(view: View)
        fun detach()
        fun loadCharacter(id: Int)
    }
}
