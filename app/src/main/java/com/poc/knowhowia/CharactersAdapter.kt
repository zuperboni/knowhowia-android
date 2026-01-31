package com.poc.knowhowia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.poc.knowhowia.model.CharacterUi

class CharactersAdapter(
    private val onItemClick: (CharacterUi) -> Unit
) : RecyclerView.Adapter<CharactersAdapter.VH>() {

    private val items = mutableListOf<CharacterUi>()

    fun submit(list: List<CharacterUi>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_character, parent, false)
        return VH(view, onItemClick)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    class VH(
        itemView: View,
        private val onItemClick: (CharacterUi) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val name: TextView = itemView.findViewById(R.id.name)
        private val subtitle: TextView = itemView.findViewById(R.id.subtitle)

        fun bind(item: CharacterUi) {
            name.text = item.name
            subtitle.text = item.subtitle
            itemView.setOnClickListener { onItemClick(item) }
        }
    }
}
