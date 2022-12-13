package com.infnet.devandroidat.main.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.infnet.devandroidat.databinding.PadariaListItemBinding
import com.infnet.devandroidat.models.PadariaComId

class PadariaComIdAdapter(val listener: PadariaComIdListener) :
    ListAdapter<
            PadariaComId,
            PadariaComIdAdapter.ViewHolder
            >(PadariaComIdDiffCallback()) {

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
        val listener: PadariaComIdListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PadariaComId, position: Int) {
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
            fun from(parent: ViewGroup, listener: PadariaComIdListener): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PadariaListItemBinding.inflate(
                    layoutInflater, parent, false
                )
                return ViewHolder(binding, listener)
            }
        }
    }

}


class PadariaComIdDiffCallback : DiffUtil.ItemCallback<PadariaComId>() {

    override fun areItemsTheSame(oldItem: PadariaComId, newItem: PadariaComId): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PadariaComId, newItem: PadariaComId): Boolean {
        return oldItem == newItem
    }
}


interface PadariaComIdListener {
    fun onEditClick(padaria: PadariaComId)
    fun onDeleteClick(padaria: PadariaComId)
}