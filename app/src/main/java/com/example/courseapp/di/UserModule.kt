package com.example.courseapp.di

import com.example.courseapp.data.local.LocalUserRepository
import com.example.courseapp.data.local.dao.UserDao
import com.example.courseapp.domain.UserRepository
import dagger.Module
import dagger.Provides

@Module
class UserModule {
    @Provides
    fun provideUserRepo(userDao: UserDao): UserRepository{
        return LocalUserRepository(userDao)
    }
}