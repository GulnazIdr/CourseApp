package com.example.user_feature.presentation

import android.text.InputFilter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common_feature.domain.DataStoreRepository
import com.example.user_feature.domain.usecases.SaveUserUseCase
import com.example.user_feature.domain.usecases.VerifyEmailUseCase
import com.example.user_feature.presentation.mappers.toUser
import com.example.user_feature.presentation.models.UserUI
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
    private val verifyEmailUseCase: VerifyEmailUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val dataStoreRepository: DataStoreRepository
)  : ViewModel() {
    private val _isEmailCorrect = MutableStateFlow<Boolean>(false)
    val isEmailCorrect: StateFlow<Boolean> = _isEmailCorrect.asStateFlow()

    private val _isPasswordCorrect = MutableStateFlow<Boolean>(false)
    val isPasswordCorrect: StateFlow<Boolean> = _isPasswordCorrect.asStateFlow()

    private val _isUserSaved = MutableStateFlow<Boolean>(false)
    val isUserSaved: StateFlow<Boolean> = _isUserSaved.asStateFlow()

    private val _isServiceDialogShowed = MutableStateFlow<Boolean>(true)

    init {
        getServiceDialogState()
    }

    val isServiceDialogShowed: StateFlow<Boolean> = _isServiceDialogShowed.asStateFlow()

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

    fun saveUser(user: UserUI) =  viewModelScope.async {
        if (isFormValid.value) {
            val res = saveUserUseCase(user.toUser())
            _isUserSaved.value = res
        }
    }

    val cyrillicFilter = InputFilter { email, _, _, _, _, _ ->
        if (Regex("[\\u0400-\\u04FF]").containsMatchIn(email.toString())) ""
        else null
    }

    fun setServiceDialogShowedState(isShowed: Boolean) = viewModelScope.launch{
        dataStoreRepository.setShowedServiceDialog(isShowed)
    }

    fun getServiceDialogState() = viewModelScope.launch {
        _isServiceDialogShowed.value = dataStoreRepository.getShowedServiceDialog()
    }
}