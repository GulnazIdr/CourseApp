package com.example.courseapp.domain.usecases

interface SaveUserUseCase {
    suspend operator fun invoke(email: String, password: String): SAVE_USER
}