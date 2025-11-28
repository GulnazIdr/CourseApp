package com.example.user_feature.domain

import com.example.common_feature.domain.DataStoreRepository
import com.example.common_feature.domain.FetchedResult
import com.example.common_feature.domain.UserRepository
import com.example.common_feature.domain.models.User
import com.example.user_feature.domain.usecases.SaveUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val dataStoreRepository: DataStoreRepository,
): SaveUserUseCase {
    override suspend fun invoke(email: String, password: String): Boolean {
        dataStoreRepository.saveCurrentUserId(email)

        val getUserRes = userRepository.getUserById(email)
        return withContext(Dispatchers.IO) {
            when (getUserRes) {
                is FetchedResult.Success<User?> -> {
                    userRepository.saveUser(User(email, password))
                    true
                }

                is FetchedResult.Error<User?> -> {
                    false
                }
            }
        }
    }
}