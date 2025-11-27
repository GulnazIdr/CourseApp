package com.example.user_feature.domain.usecases

interface VerifyEmailUseCase {
    operator fun invoke(email: String): Boolean
}

enum class SAVE_USER{SUCCESS, ERROR}