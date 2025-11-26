package com.example.courseapp.di

import com.example.courseapp.data.local.LocalUserRepositoryImpl
import com.example.courseapp.domain.SaveUserUseCaseImpl
import com.example.courseapp.domain.VerifyEmailUseCaseImpl
import com.example.courseapp.data.local.dao.UserDao
import com.example.courseapp.domain.DataStoreRepository
import com.example.courseapp.domain.UserRepository
import com.example.courseapp.domain.usecases.SaveUserUseCase
import com.example.courseapp.domain.usecases.VerifyEmailUseCase
import dagger.Module
import dagger.Provides

@Module
class UserModule {
    @Provides
    fun provideUserRepo(
        userDao: UserDao
    ): UserRepository{
        return LocalUserRepositoryImpl(userDao)
    }

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