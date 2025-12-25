package com.example.user_feature.domain.usecases

import com.example.common_feature.domain.models.User

interface FetchUsersUseCase {
    suspend operator fun invoke(): List<User>
}