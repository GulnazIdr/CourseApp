package com.example.courseapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.courseapp.data.local.LocalUserRepository
import com.example.courseapp.domain.DataStoreRepository
import javax.inject.Inject

class AuthViewModelFactory @Inject constructor(
    private val localUserRepository: LocalUserRepository,
    private val dataStoreRepository: DataStoreRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthorizationViewModel(localUserRepository, dataStoreRepository) as T
    }
}