package com.infnet.devandroidat.login.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.infnet.devandroidat.MainActivity
import com.infnet.devandroidat.R
import com.infnet.devandroidat.databinding.FragmentCadastroBinding
import com.infnet.devandroidat.login.ui.viewmodel.LoginViewModel
import com.infnet.devandroidat.utils.getTextInput
import com.infnet.devandroidat.utils.nav
import com.infnet.devandroidat.utils.toast

class CadastroFragment : Fragment() {
    val viewModel by activityViewModels<LoginViewModel> ()

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Usar a vinculação de visualizações em fragmentos
    // https://developer.android.com/topic/libraries/view-binding?hl=pt-br#fragments

    private var _binding: FragmentCadastroBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCadastroBinding.inflate(inflater, container, false)
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

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Setup ///////////////////////////////////////////////////////////////////////////////////////

    private fun setup(){
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.apply {
            btnNext.setOnClickListener {

                onNextClick()


            }
        }
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    // Eventos de clique ///////////////////////////////////////////////////////////////////////////
    private fun onNextClick() {
        if (saveInputs()) {
            nav(R.id.action_cadastroFragment_to_enderecoFragment)
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    fun saveInputs(): Boolean {
        binding.apply {
            val nome = getTextInput(inputNome)
            val email = getTextInput(inputEmail)
            val password = getTextInput(inputPassword)
            val confirmPassword = getTextInput(inputConfirmPassword)

            if ( (password == confirmPassword) && password.length > 5){
                viewModel.apply {
                    setNome(nome)
                    setEmail(email)
                    setPassword(password)
                }
                return true
            }
            return false
        }
    }

//    fun Cadastro(email: String, password: String){
//        viewModel.signOn(email, password)
//            .addOnSuccessListener {
//                toast("Cadastrado com Sucesso")
//                startMainActivity()
//            }
//            .addOnFailureListener {
//                toast("Falha ao cadastrar\n${it.message}")
//            }
//    }

//    fun startMainActivity(){
//        startActivity(Intent(requireContext(), MainActivity::class.java))
//        activity?.finish()
//    }
}