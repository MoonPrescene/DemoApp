package com.example.demoapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.demoapp.R
import com.example.demoapp.common.showToast
import com.example.demoapp.databinding.FragmentSignUpBinding
import com.example.demoapp.model.SignInRes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by viewModels()
    private lateinit var binding: FragmentSignUpBinding
    private val signUpSuccessMsg = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sign_up, container, false
        )
        binding.apply {
            signUpButton.setOnClickListener {
                checkInformation()
            }
        }
        subscribeUI()
        return binding.root
    }

    private fun subscribeUI() {
        viewModel.apply {
            signUpRes.observe(viewLifecycleOwner){
                when(it){
                    is SignInRes.Success ->{
                        requireContext().showToast(signUpSuccessMsg)
                        findNavController().popBackStack()
                    }

                    is SignInRes.Error ->{
                        requireContext().showToast(it.message)
                    }
                }
            }
        }
    }

    private fun checkInformation() {
        binding.apply {
            viewModel.login(
                username = emailEditText.text.toString(),
                password = passwordEditText.text.toString()
            )
        }
    }
}