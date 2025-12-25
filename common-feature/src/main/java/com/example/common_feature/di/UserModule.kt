package com.example.common_feature.di

import com.example.common_feature.data.LocalUserRepositoryImpl
import com.example.common_feature.data.dao.UserDao
import com.example.common_feature.domain.DataStoreRepository
import com.example.common_feature.domain.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UserModule {

    @Provides
    @Singleton
    fun provideUserRepo(
        userDao: UserDao,
        dataStoreRepository: DataStoreRepository
    ): UserRepository {
        return LocalUserRepositoryImpl(userDao, dataStoreRepository)
    }
}