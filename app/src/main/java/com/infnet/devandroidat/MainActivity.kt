package com.infnet.devandroidat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.infnet.devandroidat.databinding.ActivityMainBinding
import com.infnet.devandroidat.login.ui.viewmodel.LoginViewModel
import com.infnet.devandroidat.main.ui.MainViewModel

class MainActivity : AppCompatActivity() {

    val viewModel by viewModels<MainViewModel>()

    private lateinit var bindingActivity: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingActivity = ActivityMainBinding.inflate(layoutInflater)

        viewModel.nome.observe(this) {
            bindingActivity.tvBemVindo.text = it
        }
        setContentView(bindingActivity.root)
    }
}