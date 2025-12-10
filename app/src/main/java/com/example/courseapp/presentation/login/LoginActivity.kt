package com.example.courseapp.presentation.login

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.courseapp.R
import com.example.courseapp.app.CourseApplication
import com.example.courseapp.databinding.ActivityLoginBinding
import com.example.courseapp.presentation.main.home.MainActivity
import com.example.user_feature.presentation.AuthViewModelFactory
import com.example.user_feature.presentation.AuthorizationViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var vmFactory: AuthViewModelFactory
    private lateinit var binding: ActivityLoginBinding
    private lateinit var authorizationViewModel: AuthorizationViewModel
    private val VK_URL = "https://vk.com/"
    private val OK_URL = "https://ok.ru/"
    private var isPasswordVisible = MutableStateFlow<Boolean>(false).value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (applicationContext as CourseApplication).appComponent.inject(this)
        authorizationViewModel =
            ViewModelProvider(this, vmFactory)[AuthorizationViewModel::class.java]

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



                binding.emailField.filters = arrayOf(authorizationViewModel.cyrillicFilter)
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
            }
        }

        binding.passwordVisibSwitcher.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            val selection = binding.passwordField.selectionEnd
            binding.passwordField.inputType = InputType.TYPE_CLASS_TEXT or
                    if (isPasswordVisible) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    else InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.passwordField.setSelection(selection)

            binding.passwordVisibSwitcher.setImageResource(
                if (isPasswordVisible) R.drawable.ic_password_visib
                else R.drawable.ic_password_visib_off
            )
        }

        binding.loginButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)

            val email = binding.emailField.text.toString()
            val password = binding.passwordField.text.toString()
            authorizationViewModel.saveUser(email, password)

            lifecycleScope.launch {
                authorizationViewModel.isUserSaved
                    .first { it }
                    .let {
                        startActivity(intent)
                        finish()
                    }
            }
        }
    }
}