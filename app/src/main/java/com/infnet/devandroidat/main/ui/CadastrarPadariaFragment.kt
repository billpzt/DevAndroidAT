package com.infnet.devandroidat.main.ui.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.infnet.devandroidat.R
import com.infnet.devandroidat.databinding.FragmentCadastrarPadariaBinding
import com.infnet.devandroidat.main.ui.MainViewModel
import com.infnet.devandroidat.models.Padaria
import com.infnet.devandroidat.utils.getTextInput
import com.infnet.devandroidat.utils.navUp
import com.infnet.devandroidat.utils.toast

class CadastrarPadariaFragment : Fragment() {

    val viewModel by activityViewModels<MainViewModel>()

    private var _binding: FragmentCadastrarPadariaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCadastrarPadariaBinding.inflate(inflater, container, false)
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
        val padaria = getPadariaFromInputs()

        viewModel.cadastrarPadaria(padaria)
            .addOnSuccessListener { documentReference ->
                toast("Cadastrado com sucesso com id: ${documentReference.id}")
                navUp()
            }
            .addOnFailureListener { e ->
                toast("Falha ao cadastrar")
            }
    }

    private fun getPadariaFromInputs(): Padaria {
        binding.apply {
            return Padaria(
                nomePadaria = getTextInput(inputNomePadaria),
                nomeDono = getTextInput(inputNomeDono)
            )
        }
    }

    private fun setupViews() {
        activity?.setTitle("Cadastrar Padaria")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}