package com.example.courseapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseapp.data.local.LocalUserRepository
import com.example.courseapp.domain.DataStoreRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
    private val localUserRepository: LocalUserRepository,
    private val dataStoreRepository: DataStoreRepository
)  : ViewModel() {
    private val _isEmailCorrect = MutableStateFlow<Boolean>(false)
    val isEmailCorrect: StateFlow<Boolean> = _isEmailCorrect.asStateFlow()

    private val _isPasswordCorrect = MutableStateFlow<Boolean>(false)
    val isPasswordCorrect: StateFlow<Boolean> = _isPasswordCorrect.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

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

    fun saveUser(email: String, password: String) =  viewModelScope.async {
        if (isFormValid.value) {
            _isLoading.value = true
            dataStoreRepository.saveCurrentUserId(email)
                if (localUserRepository.getUserById(email) == null)
                    localUserRepository.saveUser(UserMainInfo(email, password))
            }
            _isLoading.value = false
        }

}