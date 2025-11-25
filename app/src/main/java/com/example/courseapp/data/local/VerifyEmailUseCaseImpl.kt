package com.example.courseapp.data.local

import com.example.courseapp.domain.usecases.VerifyEmailUseCase
import javax.inject.Inject

class VerifyEmailUseCaseImpl @Inject constructor(): VerifyEmailUseCase {
    override fun invoke(email: String): Boolean {
        val emailRegex = Regex("^[a-z0-9._]+@[a-z]+\\.[a-z]{2,}\$")

        return email.isNotEmpty() &&
                    email.matches(emailRegex) &&
                    !Regex("[\\u0400-\\u04FF]").containsMatchIn(email)
    }
}