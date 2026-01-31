package com.poc.knowhowia.details

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CharacterDetailsPresenter(
    private val repository: CharacterDetailsRepository
) : CharacterDetailsContract.Presenter {

    private var view: CharacterDetailsContract.View? = null
    private val disposables = CompositeDisposable()

    override fun attach(view: CharacterDetailsContract.View) {
        this.view = view
    }

    override fun detach() {
        view = null
        disposables.clear()
    }

    override fun loadCharacter(id: Int) {
        view?.showLoading(true)

        val d = repository.getCharacter(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { dto ->
                CharacterDetailsUi(
                    name = dto.name,
                    status = dto.status,
                    species = dto.species,
                    gender = dto.gender
                )
            }
            .doFinally { view?.showLoading(false) }
            .subscribe(
                { ui -> view?.showCharacter(ui) },
                { err -> view?.showError(err.message ?: "Erro ao carregar detalhes") }
            )

        disposables.add(d)
    }
}
