package com.example.courseapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.courseapp.app.CourseApplication
import com.example.courseapp.databinding.ActivityLoginBinding
import com.example.courseapp.presentation.MainActivity
import com.example.courseapp.presentation.login.AuthViewModelFactory
import com.example.courseapp.presentation.login.AuthorizationViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var vmFactory: AuthViewModelFactory
    private lateinit var binding: ActivityLoginBinding
    private lateinit var authorizationViewModel: AuthorizationViewModel
    private val VK_URL = "https://vk.com/"
    private val OK_URL  = "https://ok.ru/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (applicationContext as CourseApplication).appComponent.inject(this)
        authorizationViewModel = ViewModelProvider(this, vmFactory)[AuthorizationViewModel::class.java]

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

                launch {
                    authorizationViewModel.isLoading.collect {
                       if(it){
                           Toast.makeText(applicationContext, "Saving...", Toast.LENGTH_SHORT).show()
                       }
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
            lifecycleScope.launch {
                val email = binding.emailField.text.toString()
                val password = binding.passwordField.text.toString()
                authorizationViewModel.saveUser(email, password)

                startActivity(intent)
                finish()
            }
        }
    }
}