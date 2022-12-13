package com.infnet.devandroidat.main.ui.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.infnet.devandroidat.R
import com.infnet.devandroidat.databinding.FragmentCadastrarPaoBinding
import com.infnet.devandroidat.main.ui.MainViewModel
import com.infnet.devandroidat.models.Pao
import com.infnet.devandroidat.utils.getIntInput
import com.infnet.devandroidat.utils.getTextInput
import com.infnet.devandroidat.utils.navUp
import com.infnet.devandroidat.utils.toast

class CadastrarPaoFragment : Fragment() {

    val viewModel by activityViewModels<MainViewModel>()

    private var _binding: FragmentCadastrarPaoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCadastrarPaoBinding.inflate(inflater, container, false)
        val view = binding.root
        setup()
        return view
    }

    private fun setup() {
        setupViews()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnCadastrar.setOnClickListener {
            onCadastrarClick()
        }
    }

    private fun onCadastrarClick() {
        val pao = getPaoFromInputs()

        viewModel.cadastrarPao(pao)
            .addOnSuccessListener { documentReference ->
                toast("Cadastrado com sucesso com id: ${documentReference.id}")
                navUp()
            }
            .addOnFailureListener { e ->
                toast("Falha ao cadastrar")
            }
    }

    private fun getPaoFromInputs(): Pao {
        binding.apply {
            return Pao(
                nomePao = getTextInput(inputNomePao),
                tipo = getTextInput(inputTipo)
            )
        }
    }

    private fun setupViews() {
        activity?.setTitle("Cadastrar Pao")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}