package com.example.user_feature.domain.usecases

interface VerifyEmailUseCase {
    operator fun invoke(email: String): Boolean
}

