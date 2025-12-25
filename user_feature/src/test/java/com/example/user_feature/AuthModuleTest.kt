package com.example.user_feature

import com.example.common_feature.domain.DataStoreRepository
import com.example.common_feature.domain.UserRepository
import com.example.user_feature.data.SaveUserUseCaseImpl
import com.example.user_feature.domain.VerifyEmailUseCaseImpl
import com.example.user_feature.domain.usecases.SaveUserUseCase
import com.example.user_feature.domain.usecases.VerifyEmailUseCase
import dagger.Provides

class AuthModuleTest {
    @Provides
    fun provideVerifyEmailUseCase(): VerifyEmailUseCase{
        return VerifyEmailUseCaseImpl()
    }

    @Provides
    fun provideSaveUserUseCase(
        userRepository: UserRepository,
        dataStoreRepository: DataStoreRepository
    ): SaveUserUseCase{
        return SaveUserUseCaseImpl(userRepository, dataStoreRepository)
    }
}