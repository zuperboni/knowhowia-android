package com.poc.knowhowia.episodes

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.poc.knowhowia.R
import com.poc.knowhowia.details.CharacterDetailsFragment
import com.poc.knowhowia.model.CharacterUi
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.core.scope.Scope

class EpisodesFragment :
    Fragment(R.layout.fragment_episodes),
    EpisodesContract.View,
    AndroidScopeComponent {

    override val scope: Scope by fragmentScope()

    private lateinit var recycler: RecyclerView
    private lateinit var progress: ProgressBar

    private val adapter by lazy {
        EpisodesAdapter { item ->
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, CharacterDetailsFragment.newInstance(item.id))
                .addToBackStack("character_details")
                .commit()
        }
    }

    private val presenter: EpisodesContract.Presenter by lazy {
        scope.get<EpisodesContract.Presenter>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = view.findViewById(R.id.recycler)
        progress = view.findViewById(R.id.progress)

        recycler.adapter = adapter

        presenter.attach(this)
        presenter.loadCharacters()
    }

    override fun onDestroyView() {
        presenter.detach()
        super.onDestroyView()
    }

    override fun onDestroy() {
        scope.close()
        super.onDestroy()
    }

    override fun showLoading(show: Boolean) {
        progress.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showCharacters(items: List<CharacterUi>) {
        adapter.submit(items)
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    companion object {
        fun newInstance() = EpisodesFragment()
    }
}
