package com.infnet.devandroidat.login.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.infnet.devandroidat.R
import com.infnet.devandroidat.databinding.ActivityLoginBinding
import com.infnet.devandroidat.login.ui.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    val viewModel by viewModels<LoginViewModel>()

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Usar a vinculação de visualizações em atividades
    // https://developer.android.com/topic/libraries/view-binding?hl=pt-br#activities
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
}