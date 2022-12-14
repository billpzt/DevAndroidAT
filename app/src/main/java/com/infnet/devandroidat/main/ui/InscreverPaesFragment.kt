package com.infnet.devandroidat.main.ui.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.infnet.devandroidat.R
import com.infnet.devandroidat.databinding.FragmentInscreverPaesBinding
import com.infnet.devandroidat.main.ui.MainViewModel
import com.infnet.devandroidat.main.ui.adapters.InscreverPaoNaPadariaAdapter
import com.infnet.devandroidat.main.ui.adapters.InscreverPaoNaPadariaListener
import com.infnet.devandroidat.models.PaoComId
import com.infnet.devandroidat.utils.toast

class InscreverPaesFragment : Fragment() {

    val TAG = "InscreverPaesFragment"

    val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentInscreverPaesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInscreverPaesBinding.inflate(inflater, container, false)
        val view = binding.root
        setup()
        return view
    }

    val adapter = InscreverPaoNaPadariaAdapter(
        object : InscreverPaoNaPadariaListener {
            override fun onAddClick(pao: PaoComId) {
                inscreverPaoNaPadaria(pao)
            }
        }
    )

    private fun inscreverPaoNaPadaria(pao: PaoComId) {
        viewModel.inscreverPaoNaPadaria(pao)
    }

    private fun setup() {
        setupViews()
        setupClickListeners()
        setupRecyclerView()
        setupObservers()

    }

    private fun setupClickListeners() {

        binding.tilPesquisaPao.setEndIconOnClickListener {
            filtrarLista(binding.inputPesquisaPao.text.toString())
        }

    }

    private fun filtrarLista(query: String) {
        val listaAntiga = viewModel.paesComId.value
        val listaNova = mutableListOf<PaoComId>()

        listaAntiga?.forEach {
            if (it.nomePao.contains(query)){
                listaNova.add(it)
            }
        }

        atualizaRecyclerView(listaNova)

    }

    private fun onSearchClick() {
        toast("Pesquisa pelo pao: ${binding.inputPesquisaPao.text.toString()}")
    }

    private fun setupViews() {
        activity?.setTitle("Increver pao na padaria")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupRecyclerView() {
        // adapter precisa ser uma vari??vel global para ser acessada por todos os m??todos
        binding.rvInscreverPaesNaPadaria.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun setupObservers() {
        viewModel.paesComId.observe(viewLifecycleOwner) {
            atualizaRecyclerView(it)
        }
    }

    fun atualizaRecyclerView(lista: List<PaoComId>) {
        adapter.submitList(lista)
        binding.rvInscreverPaesNaPadaria.adapter = adapter
    }



}