package com.infnet.devandroidat.main.ui.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.infnet.devandroidat.R
import com.infnet.devandroidat.databinding.FragmentHomeBinding
import com.infnet.devandroidat.utils.nav

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        setup()
        return view
    }

    private fun setup() {
        setupViews()
        setupClickListeners()
    }

    private fun setupViews() {
        activity?.setTitle("Home")
    }

    private fun setupClickListeners() {
        binding.apply {
            btnPadarias.setOnClickListener {
                nav(R.id.action_homeFragment_to_padariasFragment)
            }

            btnPaes.setOnClickListener {
                nav(R.id.action_homeFragment_to_paesFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}