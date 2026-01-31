package com.poc.knowhowia

import com.poc.knowhowia.data.CharactersRepository
import com.poc.knowhowia.model.CharacterUi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class HomePresenter(
    private val repository: CharactersRepository
) : HomeContract.Presenter {

    private var view: HomeContract.View? = null
    private val disposables = CompositeDisposable()

    override fun attach(view: HomeContract.View) {
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
