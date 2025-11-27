package com.example.user_feature.domain

import com.example.common_feature.domain.DataStoreRepository
import com.example.common_feature.domain.FetchedResult
import com.example.common_feature.domain.UserRepository
import com.example.common_feature.domain.models.User
import com.example.user_feature.domain.usecases.SAVE_USER
import com.example.user_feature.domain.usecases.SaveUserUseCase
import javax.inject.Inject

class SaveUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val dataStoreRepository: DataStoreRepository,
): SaveUserUseCase {
    override suspend fun invoke(email: String, password: String): SAVE_USER {
        dataStoreRepository.saveCurrentUserId(email)

        val res = when(userRepository.getUserById(email)){
            is FetchedResult.Success<User?> ->
                userRepository.saveUser(User(email, password))
            is FetchedResult.Error<User?> -> {
                return SAVE_USER.ERROR
            }
        }

        return when(res){
            is FetchedResult.Success<Boolean> -> {
                SAVE_USER.SUCCESS
            }

            is FetchedResult.Error<Boolean> ->
                SAVE_USER.ERROR
        }
    }
}