package com.example.user_feature.domain.usecases

interface SaveUserUseCase {
    suspend operator fun invoke(email: String, password: String): Boolean
}