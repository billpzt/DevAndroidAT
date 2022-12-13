package com.infnet.devandroidat.login.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.infnet.devandroidat.R
import com.infnet.devandroidat.databinding.FragmentCadastroBinding
import com.infnet.devandroidat.databinding.FragmentEnderecoBinding
import com.infnet.devandroidat.login.ui.viewmodel.LoginViewModel
import com.infnet.devandroidat.models.Endereco
import com.infnet.devandroidat.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class EnderecoFragment : Fragment() {
    val TAG = "Endereco"

    val viewModel by activityViewModels<LoginViewModel> ()

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Usar a vinculação de visualizações em fragmentos
    // https://developer.android.com/topic/libraries/view-binding?hl=pt-br#fragments

    private var _binding: FragmentEnderecoBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEnderecoBinding.inflate(inflater, container, false)
        val view = binding.root

        setup()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private fun setup() {
        setupClickListeners()
        setupAfterTextChanged()
    }

    private fun setupAfterTextChanged() {
        binding.inputCep.doAfterTextChanged {
            val entrada = it.toString()
            val entradaModificada = formatInputCep( entrada )

            if (entrada.length != entradaModificada.length ){
                Log.i(TAG, "entrada: $entrada")
                binding.inputCep.setText( entradaModificada )
                binding.inputCep.setSelection(entradaModificada.length)
            }

        }
    }

    private fun setupClickListeners() {
        binding.btnConsultaCep.setOnClickListener {
            onConsultaCepClick()
        }

        binding.btnCadastrar.setOnClickListener {
            onCadastrarClick()
        }

        binding.tilInputCep.setEndIconOnClickListener {
            binding.inputCep.setText("")
            binding.tilInputCep.helperText = "Campo obrigatório."
        }
    }

    private fun onCadastrarClick() {
        if (validateInputs()) {
            viewModel.cadastrarUsuarioAuth().addOnSuccessListener {
                val uid = it.user?.uid ?: ""
                viewModel.cadastrarUsuarioFirestore(uid)
                toast("Sucesso ao cadastrar")
            }
                .addOnFailureListener {
                    toast("Falha ao cadastrar")
                }
        }
    }

    fun onConsultaCepClick() {

        lifecycleScope.launch(Dispatchers.Main) {
            val cepFormatado = binding.inputCep.text.toString()
            val cepSimples = getCepNumberFromFormat(cepFormatado)
            Log.i(TAG, "cepFormatado: $cepFormatado" )
            Log.i(TAG, "cepSimples: $cepSimples" )

            fillForm(getCepEndereco(cepSimples))
        }
    }

    suspend fun getCepEndereco(cep: String): Endereco {
        val enderecoAsync = lifecycleScope
            .async(Dispatchers.IO) {
                return@async viewModel.fetchEnderecoFromCep(cep)
            }
        return enderecoAsync.await()
    }

    fun fillForm(endereco: Endereco){

        cepRecuperado = true

        binding.apply {
            tvLogradouro.text = "Logradouro: ${endereco.logradouro}"
            tvBairro.text = "Bairro: ${endereco.bairro}"
            tvCidade.text = "Cidade: ${endereco.cidade}"
            tvEstado.text = "Estado: ${endereco.estado}"
        }

        viewModel.setEndereco(endereco)
    }

    // 00000-000
    fun formatInputCep(cep: String): String {
        Log.i(TAG, cep )
        return when(cep.length){
            5 -> "${cep}-"
            10 -> cep.substring(0,9)
            else -> cep
        }
    }

    // 12345678
    fun getCepNumberFromFormat(cep: String ) : String {
        return cep.replace(".", "").replace("-", "")
    }

    var cepRecuperado = false

    fun validateInputs(): Boolean {

        if(!cepRecuperado) {
            toast("Preencha o CEP")
            return false
        }
        val numero = binding.inputNumero.text.toString()
        if (numero.isEmpty()) {
            toast("Preencha o numero")
            return false
        }

        viewModel.setNumero(numero)
        val complemento = binding.inputComplemento.toString()
        if (!complemento.isNullOrBlank()) {
            viewModel.setComplemento("")
        } else {
            viewModel.setComplemento(complemento)
        }
        return true
    }

}