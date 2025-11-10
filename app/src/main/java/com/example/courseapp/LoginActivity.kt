package com.example.courseapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.courseapp.databinding.ActivityLoginBinding
import com.example.courseapp.presentation.MainActivity
import com.example.courseapp.presentation.login.AuthorizationViewModel
import kotlinx.coroutines.launch
import androidx.core.net.toUri

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val authorizationViewModel: AuthorizationViewModel by viewModels()
    private val VK_URL = "https://vk.com/"
    private val OK_URL  = "https://ok.ru/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    authorizationViewModel.isPasswordCorrect.collect {
                        binding.passwordErrorField.visibility = if (it) View.GONE else View.VISIBLE
                    }
                }

                launch {
                    authorizationViewModel.isEmailCorrect.collect {
                        binding.emailErrorField.visibility = if (it) View.GONE else View.VISIBLE
                    }
                }

                launch {
                    authorizationViewModel.isFormValid.collect {
                        binding.loginButton.isEnabled = it
                    }
                }
            }
        }
        binding.emailField.doOnTextChanged { email, _, _, _ ->
            authorizationViewModel.verifyEmail(email.toString())
        }
        binding.passwordField.doOnTextChanged { password, _, _, _ ->
            authorizationViewModel.verifyPassword(password.toString())
        }

        binding.vkBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, VK_URL.toUri())
            startActivity(intent)
        }

        binding.odnoklasnikiBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, OK_URL.toUri())
            startActivity(intent)
        }


        binding.loginButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}