package com.example.user_feature.data

import com.example.common_feature.data.mappers.UserMapper
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
): SaveUserUseCase,  UserMapper() {
    override suspend fun invoke(user: User): Boolean {
        dataStoreRepository.saveUserToList(user.toUserSerial())
        dataStoreRepository.setCurrentUser(user.toUserSerial())

        return withContext(Dispatchers.IO) {
            val saveUserRes = userRepository.saveUser(user)
            var isSaved = false
            when (saveUserRes) {
                is FetchedResult.Success<Boolean> -> isSaved = saveUserRes.data!!
                is FetchedResult.Error<Boolean> -> {}
            }
            isSaved
        }
    }
}