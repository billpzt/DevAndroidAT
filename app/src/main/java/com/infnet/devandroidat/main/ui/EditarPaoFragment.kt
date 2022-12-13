package com.infnet.devandroidat.main.ui.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.infnet.devandroidat.R
import com.infnet.devandroidat.databinding.FragmentEditarPaoBinding
import com.infnet.devandroidat.main.ui.MainViewModel
import com.infnet.devandroidat.models.Pao
import com.infnet.devandroidat.utils.getIntInput
import com.infnet.devandroidat.utils.getTextInput
import com.infnet.devandroidat.utils.navUp

class EditarPaoFragment : Fragment() {

    val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentEditarPaoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarPaoBinding.inflate(inflater, container, false)
        val view = binding.root
        setup()

        return view
    }

    private fun setup() {
        setupViews()
        setupObservers()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnAtualizar.setOnClickListener {
            onAtualizarClick()
        }
    }

    private fun onAtualizarClick() {
        val pao = getPaoFromInputs()
        viewModel.atualizaPao(pao)
        navUp()
    }

    private fun getPaoFromInputs(): Pao {
        binding.apply {
            return Pao(
                nomePao = getTextInput(inputNomePao),
                tipo = getTextInput(inputTipo)
            )
        }
    }

    private fun setupObservers() {
        viewModel.selectedPaoComId.observe(viewLifecycleOwner) {
            binding.apply {
                inputNomePao.setText(it.nomePao)
                inputTipo.setText(it.tipo)
            }
        }
    }

    private fun setupViews() {
        activity?.setTitle("Editar")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

