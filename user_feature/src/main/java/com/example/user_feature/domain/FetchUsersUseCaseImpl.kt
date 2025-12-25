package com.example.user_feature.domain

import com.example.common_feature.domain.FetchedResult
import com.example.common_feature.domain.UserRepository
import com.example.common_feature.domain.models.User
import com.example.user_feature.domain.usecases.FetchUsersUseCase
import javax.inject.Inject

class FetchUsersUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
): FetchUsersUseCase {
    override suspend fun invoke(): List<User> {
        val fetched = userRepository.getUsers()
        var list = emptyList<User>()
        when(fetched){
            is FetchedResult.Success<List<User>> -> list = fetched.data!!
            is FetchedResult.Error<List<User>> -> {}
        }
        return list
    }
}