package com.example.courseapp.presentation.login

import android.text.InputFilter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseapp.domain.usecases.SAVE_USER
import com.example.courseapp.domain.usecases.SaveUserUseCase
import com.example.courseapp.domain.usecases.VerifyEmailUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
    private val verifyEmailUseCase: VerifyEmailUseCase,
    private val saveUserUseCase: SaveUserUseCase
)  : ViewModel() {
    private val _isEmailCorrect = MutableStateFlow<Boolean>(false)
    val isEmailCorrect: StateFlow<Boolean> = _isEmailCorrect.asStateFlow()

    private val _isPasswordCorrect = MutableStateFlow<Boolean>(false)
    val isPasswordCorrect: StateFlow<Boolean> = _isPasswordCorrect.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isUserSaved = MutableStateFlow<Boolean>(false)
    val isUserSaved: StateFlow<Boolean> = _isUserSaved.asStateFlow()

     val isFormValid = combine(
         _isEmailCorrect,
         _isPasswordCorrect
     ) { isEmailCorrect, isPasswordCorrect ->
         isEmailCorrect && isPasswordCorrect
     }.stateIn(viewModelScope, SharingStarted.Companion.Eagerly, false)

    fun verifyEmail(email: String) {
        _isEmailCorrect.value = verifyEmailUseCase(email)
    }

    fun verifyPassword(password: String){
        _isPasswordCorrect.value = password.isNotEmpty()
    }

    fun saveUser(email: String, password: String) =  viewModelScope.async {
        if (isFormValid.value) {
            _isLoading.value = true
            val res = saveUserUseCase(email, password)
            when(res){
                SAVE_USER.SUCCESS -> _isUserSaved.value = true
                SAVE_USER.ERROR ->{
                    _isUserSaved.value = false
                }
            }
        }
            _isLoading.value = false
    }

    val cyrillicFilter = InputFilter { email, _, _, _, _, _ ->
        if (Regex("[\\u0400-\\u04FF]").containsMatchIn(email.toString())) {
            ""
        } else {
            null
        }
    }

}