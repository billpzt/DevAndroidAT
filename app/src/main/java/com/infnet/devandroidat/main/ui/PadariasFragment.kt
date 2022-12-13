package com.infnet.devandroidat.main.ui.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.infnet.devandroidat.R

class PadariasFragment : Fragment() {

    val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentPadariasBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPadariasBinding.inflate(inflater, container, false)
        val view = binding.root
        setup()
        return view
    }

    private fun setup() {
        setupViews()
        setupClickListeners()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupClickListeners() {
        binding.btnCadastrar.setOnClickListener {
            nav(R.id.action_padariasFragment_to_cadastrarPadariaFragment)
        }
    }

    private fun setupViews() {
        activity?.setTitle("Padarias")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    // Existe maneira melhor!!!
    //    fun getPadarias() {
    //        viewModel.getPadarias()
    //    }


    val adapter = PadariaComIdAdapter(
        object : PadariaComIdListener {
            override fun onEditClick(padaria: PadariaComId) {
                viewModel.setSelectedPadariaComId(padaria)
                nav(R.id.action_padariasFragment_to_editarPadariaFragment)
            }

            override fun onDeleteClick(padaria: PadariaComId) {
                viewModel.deletePadaria(padaria.id)
            }
        }
    )

    private fun setupRecyclerView() {
        // adapter precisa ser uma variável global para ser acessada por todos os métodos
        binding.rvPadarias.adapter = adapter
        binding.rvPadarias.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun setupObservers() {
        viewModel.padariasComId.observe(viewLifecycleOwner) {
            atualizaRecyclerView(it)
        }
    }

    fun atualizaRecyclerView(lista: List<PadariaComId>) {
        adapter.submitList(lista)
        binding.rvPadarias.adapter = adapter
    }


}