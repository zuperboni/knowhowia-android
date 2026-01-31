package com.poc.knowhowia.details

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.poc.knowhowia.R
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.core.scope.Scope

class CharacterDetailsFragment :
    Fragment(R.layout.fragment_character_details),
    CharacterDetailsContract.View, AndroidScopeComponent {

    override val scope: Scope by fragmentScope()

    private lateinit var progress: ProgressBar
    private lateinit var name: TextView
    private lateinit var status: TextView
    private lateinit var species: TextView
    private lateinit var gender: TextView
    private val presenter: CharacterDetailsContract.Presenter = scope.get<CharacterDetailsContract.Presenter>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progress = view.findViewById(R.id.progress)
        name = view.findViewById(R.id.name)
        status = view.findViewById(R.id.status)
        species = view.findViewById(R.id.species)
        gender = view.findViewById(R.id.gender)

        val id = requireArguments().getInt(ARG_ID)

        presenter.attach(this)
        presenter.loadCharacter(id)
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

    override fun showCharacter(data: CharacterDetailsUi) {
        name.text = data.name
        status.text = "Status: ${data.status}"
        species.text = "Espécie: ${data.species}"
        gender.text = "Gênero: ${data.gender}"
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val ARG_ID = "arg_character_id"

        fun newInstance(id: Int) = CharacterDetailsFragment().apply {
            arguments = Bundle().apply { putInt(ARG_ID, id) }
        }
    }
}
