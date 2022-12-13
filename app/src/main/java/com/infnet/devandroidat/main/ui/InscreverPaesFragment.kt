package com.infnet.devandroidat.main.ui.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.infnet.devandroidat.R

class InscreverPaosFragment : Fragment() {

    val TAG = "InscreverPaosFragment"

    val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentInscreverPaosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInscreverPaosBinding.inflate(inflater, container, false)
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
        val listaAntiga = viewModel.paosComId.value
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
        // adapter precisa ser uma variável global para ser acessada por todos os métodos
        binding.rvInscreverPaosNaPadaria.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun setupObservers() {
        viewModel.paosComId.observe(viewLifecycleOwner) {
            atualizaRecyclerView(it)
        }
    }

    fun atualizaRecyclerView(lista: List<PaoComId>) {
        adapter.submitList(lista)
        binding.rvInscreverPaosNaPadaria.adapter = adapter
    }




}