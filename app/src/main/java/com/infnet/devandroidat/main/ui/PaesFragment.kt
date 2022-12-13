package com.infnet.devandroidat.main.ui.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.infnet.devandroidat.R
import com.infnet.devandroidat.databinding.FragmentPaesBinding
import com.infnet.devandroidat.main.ui.MainViewModel
import com.infnet.devandroidat.models.PaoComId
import com.infnet.devandroidat.utils.nav

class PaesFragment : Fragment() {

    val TAG = "PaesFragment"

    val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentPaesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaesBinding.inflate(inflater, container, false)
        val view = binding.root
        setup()
        return view
    }

    val adapter = PaoComIdAdapter(
        object : PaoComIdListener {
            override fun onEditClick(pao: PaoComId) {
                viewModel.setSelectedPaoComId(pao)
                nav(R.id.action_paesFragment_to_editarPaoFragment)
            }

            override fun onDeleteClick(pao: PaoComId) {
                viewModel.deletePao(pao)
            }
        }
    )

    private fun setup() {
        setupViews()

        setupClickListeners()

        setupRecyclerView()

        setupObservers()

    }

    private fun setupClickListeners() {
        binding.btnCadastrar.setOnClickListener {
            nav(R.id.action_paesFragment_to_cadastrarPaoFragment)
        }
    }

    private fun setupViews() {
        activity?.setTitle("Paes")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupRecyclerView() {
        // adapter precisa ser uma variável global para ser acessada por todos os métodos
        binding.rvPaes.layoutManager = LinearLayoutManager(
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
        binding.rvPaes.adapter = adapter
    }


}