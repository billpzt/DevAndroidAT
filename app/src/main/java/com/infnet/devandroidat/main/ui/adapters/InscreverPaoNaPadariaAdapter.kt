package com.infnet.devandroidat.main.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.infnet.devandroidat.databinding.FragmentInscreverPaoNaPadariaListItemBinding
import com.infnet.devandroidat.models.PaoComId

class InscreverPaoNaPadariaAdapter(val listener: InscreverPaoNaPadariaListener) :
    ListAdapter<
            PaoComId,
            InscreverPaoNaPadariaAdapter.ViewHolder
            >(InscreverPaoNaPadariaDiffCallback()) {

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
        val binding: FragmentInscreverPaoNaPadariaListItemBinding,
        val listener: InscreverPaoNaPadariaListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PaoComId, position: Int) {
            binding.apply {

                paoNome.text = item.nomePao
                tipo.text = item.tipo

                ivAdd.setOnClickListener{
                    listener.onAddClick(item)
                }

            }
        }

        companion object {
            fun from(parent: ViewGroup, listener: InscreverPaoNaPadariaListener): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentInscreverPaoNaPadariaListItemBinding.inflate(
                    layoutInflater, parent, false
                )
                return ViewHolder(binding, listener)
            }
        }
    }

}


class InscreverPaoNaPadariaDiffCallback : DiffUtil.ItemCallback<PaoComId>() {

    override fun areItemsTheSame(oldItem: PaoComId, newItem: PaoComId): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PaoComId, newItem: PaoComId): Boolean {
        return oldItem == newItem
    }
}


interface InscreverPaoNaPadariaListener {
    fun onAddClick(pao: PaoComId)
}