package com.example.courseapp.data.mappers

import com.example.courseapp.data.local.entity.UserEntity
import com.example.courseapp.presentation.login.UserMainInfo

abstract class UserMapper {
    protected fun UserMainInfo.toLocalUser(): UserEntity{
        return UserEntity(
            email = email,
            password = password
        )
    }

    protected fun UserEntity.toUserUI(): UserMainInfo{
        return UserMainInfo(
            email = email,
            password = password
        )
    }
}