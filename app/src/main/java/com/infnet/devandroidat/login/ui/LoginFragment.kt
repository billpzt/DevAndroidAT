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
import com.infnet.devandroidat.databinding.FragmentLoginBinding
import com.infnet.devandroidat.login.ui.viewmodel.LoginViewModel
import com.infnet.devandroidat.utils.getTextInput
import com.infnet.devandroidat.utils.nav
import com.infnet.devandroidat.utils.toast

class LoginFragment : Fragment() {

    val viewModel by activityViewModels<LoginViewModel>()

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Usar a vinculação de visualizações em fragmentos
    // https://developer.android.com/topic/libraries/view-binding?hl=pt-br#fragments

    private var _binding: FragmentLoginBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
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

    // setup ///////////////////////////////////////////////////////////////////////////////////////

    private fun setup() {
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.apply {

            btnSignIn.setOnClickListener {
                onSignInClick()
            }

            btnSignOn.setOnClickListener {
                onSignOnClick()
            }

        }
    }


    private fun onSignOnClick() {
        nav(R.id.action_loginFragment_to_cadastroFragment)
    }

    // Pega os inputs da tela e chama a função de login
    private fun onSignInClick() {
        val email = getTextInput(binding.inputEmail)
        val password = getTextInput(binding.inputPassword)
        signIn(email, password)

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////


    // Chama a função de login do viewModel e trata a resposta: Sucesso / Falha
    private fun signIn(email: String, password: String){

        viewModel.logar(email, password)
            .addOnSuccessListener {
                toast("Logado com Sucesso")
                startMainActivity()
            }
            .addOnFailureListener {
                toast("Falha ao Logar\n${it.message}")
            }
    }

    fun startMainActivity(){
        startActivity(Intent(requireContext(), MainActivity::class.java))
        activity?.finish()
    }
}