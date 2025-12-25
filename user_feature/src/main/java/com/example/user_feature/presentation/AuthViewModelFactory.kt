package com.example.user_feature.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.common_feature.domain.DataStoreRepository
import com.example.user_feature.domain.usecases.SaveUserUseCase
import com.example.user_feature.domain.usecases.VerifyEmailUseCase
import javax.inject.Inject

class AuthViewModelFactory @Inject constructor(
    private val verifyEmailUseCase: VerifyEmailUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val dataStoreRepository: DataStoreRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthorizationViewModel(
            verifyEmailUseCase, saveUserUseCase, dataStoreRepository
        ) as T
    }
}