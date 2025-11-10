package com.example.courseapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class AuthorizationViewModel: ViewModel() {
    private val _isEmailCorrect = MutableStateFlow<Boolean>(false)
    val isEmailCorrect: StateFlow<Boolean> = _isEmailCorrect.asStateFlow()

    private val _isPasswordCorrect = MutableStateFlow<Boolean>(false)
    val isPasswordCorrect: StateFlow<Boolean> = _isPasswordCorrect.asStateFlow()

     val isFormValid = combine(
         _isEmailCorrect,
         _isPasswordCorrect
     ) { isEmailCorrect, isPasswordCorrect ->
         isEmailCorrect && isPasswordCorrect
     }.stateIn(viewModelScope, SharingStarted.Companion.Eagerly, false)

    fun verifyEmail(email: String){
        val emailRegex = Regex("^[a-z0-9._]+@[a-z]+\\.[a-z]{2,}\$")

        _isEmailCorrect.value =
            email.isNotEmpty() &&
            email.matches(emailRegex) &&
            !Regex("[\\u0400-\\u04FF]").containsMatchIn(email)
    }

    fun verifyPassword(password: String){
        _isPasswordCorrect.value = password.isNotEmpty()
    }
}