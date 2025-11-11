package com.example.courseapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
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
import androidx.lifecycle.ViewModelProvider
import com.example.courseapp.app.CourseApplication
import com.example.courseapp.presentation.login.AuthViewModelFactory
import com.example.courseapp.presentation.main.CourseMainVIewModelFactory
import com.example.courseapp.presentation.main.CourseViewModel
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var vmFactory: AuthViewModelFactory
    private lateinit var binding: ActivityLoginBinding
    private lateinit var authorizationViewModel: AuthorizationViewModel
    private val VK_URL = "https://vk.com/"
    private val OK_URL  = "https://ok.ru/"

    private var loadingDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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
                        if(it) showLoading()
                        else loadingDialog?.dismiss()
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
            authorizationViewModel.saveUser(
                binding.emailField.text.toString(), binding.passwordField.text.toString()
            )
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun showLoading(){
        if (loadingDialog == null) {
            loadingDialog = AlertDialog.Builder(applicationContext)
                .setMessage("Loading...")
                .setCancelable(false)
                .create()
        }
        loadingDialog?.show()
    }
}