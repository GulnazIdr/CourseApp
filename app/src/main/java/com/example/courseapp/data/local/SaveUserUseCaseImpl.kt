package com.example.courseapp.data.local

import com.example.courseapp.domain.DataStoreRepository
import com.example.courseapp.domain.FetchedResult
import com.example.courseapp.domain.UserRepository
import com.example.courseapp.domain.models.User
import com.example.courseapp.domain.usecases.SAVE_USER
import com.example.courseapp.domain.usecases.SaveUserUseCase
import com.example.courseapp.presentation.login.models.UserUi
import com.example.courseapp.presentation.mappers.toUser
import javax.inject.Inject

class SaveUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val dataStoreRepository: DataStoreRepository,
): SaveUserUseCase {
    override suspend fun invoke(email: String, password: String): SAVE_USER {
        dataStoreRepository.saveCurrentUserId(email)

        val res = when(userRepository.getUserById(email)){
            is FetchedResult.Success<User?> ->
                userRepository.saveUser(UserUi(email, password).toUser())
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

