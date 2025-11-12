package com.example.courseapp.data.local

import com.example.courseapp.data.local.dao.UserDao
import com.example.courseapp.data.mappers.UserMapper
import com.example.courseapp.domain.UserRepository
import com.example.courseapp.presentation.login.UserMainInfo
import javax.inject.Inject

class LocalUserRepository @Inject constructor(
    private val userDao: UserDao,
): UserMapper(), UserRepository{

    override suspend fun saveUser(user: UserMainInfo) {
        userDao.addNewUser(user.toLocalUser())
    }

    override suspend fun getUserById(id: String): UserMainInfo? {
        return userDao.getUserById(id)?.toUserUI()
    }
}