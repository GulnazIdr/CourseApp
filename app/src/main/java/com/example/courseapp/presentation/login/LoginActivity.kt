package com.example.courseapp.presentation.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.InputType
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
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
import com.example.user_feature.presentation.models.UserUI
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    @Inject lateinit var vmFactory: AuthViewModelFactory
    private lateinit var binding: ActivityLoginBinding
    private lateinit var authorizationViewModel: AuthorizationViewModel
    private val VK_URL = "https://vk.com/"
    private val OK_URL = "https://ok.ru/"
    private var isPasswordVisible = false
    private var serviceDialog: AlertDialog? = null

    @SuppressLint("QueryPermissionsNeeded")
    @RequiresApi(Build.VERSION_CODES.P)
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

                launch {
                    authorizationViewModel.isServiceDialogShowed.collect {
                        if(!it) showServiceDialog()
                    }
                }
            }
        }

        binding.emailField.filters = arrayOf(authorizationViewModel.cyrillicFilter)

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

            val email = binding.emailField.text.toString()
            val password = binding.passwordField.text.toString()
            authorizationViewModel.saveUser(UserUI(email, password))

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

    override fun onResume() {
        super.onResume()
        if (serviceDialog != null && serviceDialog!!.isShowing) {
            serviceDialog!!.dismiss()
            serviceDialog = null
        }
    }


    @RequiresApi(Build.VERSION_CODES.P)
    private fun showServiceDialog(){
        try {
            serviceDialog = MaterialAlertDialogBuilder(this@LoginActivity)
                .setTitle(R.string.autofill_title)
                .setMessage(R.string.serviceDialog)
                .setPositiveButton(R.string.learn_more) { _, _ ->
                    try {
                        val intent =
                            Intent(Settings.ACTION_REQUEST_SET_AUTOFILL_SERVICE).apply {
                                data =
                                    "package:${applicationContext.packageName}".toUri()
                            }
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        application.startActivity(intent)

                    } catch (e: Exception) {
                        Log.e("Error redirecting to settings", e.message.toString())
                    }
                }
                .setNegativeButton(R.string.not_now, null)
                .create()

            serviceDialog!!.show()

            authorizationViewModel.setServiceDialogShowedState(true)
        }catch (e: IllegalArgumentException){
            Log.e("SHOWING SERVICE DIALOG", e.message.toString())
        }
    }
}