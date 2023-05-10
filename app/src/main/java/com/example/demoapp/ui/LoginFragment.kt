package com.example.demoapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.demoapp.MainActivity
import com.example.demoapp.R
import com.example.demoapp.common.showToast
import com.example.demoapp.databinding.FragmentLoginBinding
import com.example.demoapp.model.SignInRes
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.apply {
            signInButton.setOnClickListener {
                checkInformation()
            }
        }
        subscribeUI()
        return binding.root
    }

    private fun subscribeUI() {
        viewModel.apply {
            signInRes.observe(viewLifecycleOwner) {
                when (it) {
                    is SignInRes.Success -> {
                        val intent = Intent(
                            requireActivity(), MainActivity::class.java
                        )
                        startActivity(intent)
                    }

                    is SignInRes.Error -> {
                        requireContext().showToast(
                            it.message
                        )
                    }
                }
            }
        }
    }

    private fun checkInformation() {
        binding.apply {
            if (emailEditText.text!!.isNotEmpty() && passwordEditText.text!!.isNotEmpty()) {
                viewModel.login(emailEditText.text.toString(), passwordEditText.text.toString())
            }
        }
    }

    /*private fun signIn() {
        binding.apply {
            firebaseAuth.signInWithEmailAndPassword(
                emailEditText.text.toString(),
                passwordEditText.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    val intent = Intent(
                        requireActivity(), MainActivity::class.java
                    )
                    startActivity(intent)
                } else {
                    requireContext().showToast(it.exception.toString())
                }
            }
        }
    }*/
}