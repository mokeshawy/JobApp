package com.example.jobapp.welcomescreenfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.jobapp.R
import com.example.jobapp.databinding.FragmentWelcomeScreenBinding

class WelcomeScreenFragment : Fragment() {

    lateinit var binding : FragmentWelcomeScreenBinding
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWelcomeScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // go from welcome to home page.
        binding.btnStart.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeScreenFragment_to_homeScreenFragment)
        }
    }
}