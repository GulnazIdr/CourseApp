package com.example.courseapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.courseapp.data.local.LocalUserRepositoryImpl
import com.example.courseapp.domain.DataStoreRepository
import com.example.courseapp.domain.usecases.SaveUserUseCase
import com.example.courseapp.domain.usecases.VerifyEmailUseCase
import javax.inject.Inject

class AuthViewModelFactory @Inject constructor(
    private val verifyEmailUseCase: VerifyEmailUseCase,
    private val saveUserUseCase: SaveUserUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthorizationViewModel(verifyEmailUseCase, saveUserUseCase) as T
    }
}