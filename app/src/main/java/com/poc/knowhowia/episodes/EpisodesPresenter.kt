package com.poc.knowhowia.episodes

import com.poc.knowhowia.model.CharacterUi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class EpisodesPresenter(
    private val repository: EpisodesRepository
) : EpisodesContract.Presenter {

    private var view: EpisodesContract.View? = null
    private val disposables = CompositeDisposable()

    override fun attach(view: EpisodesContract.View) {
        this.view = view
    }

    override fun detach() {
        view = null
        disposables.clear()
    }

    override fun loadCharacters() {
        view?.showLoading(true)

        val disposable = repository.getFirstPage()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { response ->
                response.results.map {
                    CharacterUi(
                        id = it.id,
                        name = it.name,
                        subtitle = "${it.species} • ${it.status}"
                    )
                }
            }
            .doFinally { view?.showLoading(false) }
            .subscribe(
                { list -> view?.showCharacters(list) },
                { err -> view?.showError(err.message ?: "Erro ao carregar personagens") }
            )

        disposables.add(disposable)
    }
}
