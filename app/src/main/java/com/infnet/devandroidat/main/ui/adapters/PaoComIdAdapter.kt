package com.infnet.devandroidat.main.ui.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.infnet.devandroidat.databinding.PaoListItemBinding
import com.infnet.devandroidat.models.PaoComId

class PaoComIdAdapter(val listener: PaoComIdListener) :
    ListAdapter<
            PaoComId,
            PaoComIdAdapter.ViewHolder
            >(PaoComIdDiffCallback()) {

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
        val binding: PaoListItemBinding,
        val listener: PaoComIdListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PaoComId, position: Int) {
            binding.apply {

                paoNome.text = item.nomePao
                tipo.text = item.tipo

                ivEdit.setOnClickListener {
                    listener.onEditClick(item)
                }
                ivDelete.setOnClickListener {
                    listener.onDeleteClick(item)
                }

            }
        }

        companion object {
            fun from(parent: ViewGroup, listener: PaoComIdListener): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PaoListItemBinding.inflate(
                    layoutInflater, parent, false
                )
                return ViewHolder(binding, listener)
            }
        }
    }

}


class PaoComIdDiffCallback : DiffUtil.ItemCallback<PaoComId>() {

    override fun areItemsTheSame(oldItem: PaoComId, newItem: PaoComId): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PaoComId, newItem: PaoComId): Boolean {
        return oldItem == newItem
    }
}


interface PaoComIdListener {
    fun onEditClick(pao: PaoComId)
    fun onDeleteClick(pao: PaoComId)
}