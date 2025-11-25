package com.example.courseapp.domain.usecases

import com.example.courseapp.data.local.SAVE_USER

interface SaveUserUseCase {
    suspend operator fun invoke(email: String, password: String): SAVE_USER
}