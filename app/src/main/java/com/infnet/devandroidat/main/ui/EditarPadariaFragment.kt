package com.infnet.devandroidat.main.ui.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.infnet.devandroidat.R
import com.infnet.devandroidat.databinding.FragmentEditarPadariaBinding
import com.infnet.devandroidat.main.ui.MainViewModel
import com.infnet.devandroidat.models.Padaria
import com.infnet.devandroidat.utils.getTextInput
import com.infnet.devandroidat.utils.nav
import com.infnet.devandroidat.utils.navUp

class EditarPadariaFragment : Fragment() {

    val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentEditarPadariaBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarPadariaBinding.inflate(inflater, container, false)
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
        binding.apply{
            btnAtualizar.setOnClickListener {
                onAtualizarClick()
            }

            btnInscreverPaes.setOnClickListener {
                onIncreverPaosClick()
            }
        }


    }

    private fun onIncreverPaosClick() {
        nav(R.id.action_editarPadariaFragment_to_inscreverPaosFragment)
    }

    private fun onAtualizarClick() {
        val padaria = getPadariaFromInputs()
        viewModel.atualizaPadaria(padaria)
        navUp()
    }

    private fun getPadariaFromInputs(): Padaria {
        binding.apply {
            return Padaria(
                nomePadaria = getTextInput(inputNomePadaria),
                nomeDono = getTextInput(inputNomeDono)
            )
        }
    }

    private fun setupObservers() {
        viewModel.selectedPadariaComId.observe(viewLifecycleOwner){
            binding.apply {
                inputNomePadaria.setText(it.nomePadaria)
                inputNomeDono.setText(it.nomeDono)
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