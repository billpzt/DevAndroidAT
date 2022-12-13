package com.infnet.devandroidat.main.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.infnet.devandroidat.databinding.PadariaListItemBinding
import com.infnet.devandroidat.models.Padaria

class PadariaAdapter(val listener: PadariaListener) :
    ListAdapter<
            Padaria,
            PadariaAdapter.ViewHolder
            >(PadariaDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder.from(parent, listener)
    }

    class ViewHolder private constructor(
        val binding: PadariaListItemBinding,
        val listener: PadariaListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Padaria, position: Int) {
            binding.apply {
                padariaNome.text = item.nomePadaria
                padariaDono.text = item.nomeDono

                ivEdit.setOnClickListener {
                    listener.onEditClick(item)
                }
                ivDelete.setOnClickListener {
                    listener.onDeleteClick(item)
                }

            }
        }

        companion object {
            fun from(parent: ViewGroup, listener: PadariaListener): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PadariaListItemBinding.inflate(
                    layoutInflater, parent, false
                )
                return ViewHolder(binding, listener)
            }
        }
    }

}


class PadariaDiffCallback : DiffUtil.ItemCallback<Padaria>() {

    override fun areItemsTheSame(oldItem: Padaria, newItem: Padaria): Boolean {
        return oldItem.nomePadaria == newItem.nomePadaria
    }

    override fun areContentsTheSame(oldItem: Padaria, newItem: Padaria): Boolean {
        return oldItem == newItem
    }
}


interface PadariaListener {
    fun onEditClick(padaria: Padaria)
    fun onDeleteClick(padaria:Padaria)
}
