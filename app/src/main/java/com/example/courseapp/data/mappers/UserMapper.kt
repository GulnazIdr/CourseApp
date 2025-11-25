package com.example.courseapp.data.mappers

import com.example.courseapp.data.local.entity.UserEntity
import com.example.courseapp.domain.models.User

abstract class UserMapper {
    protected fun UserEntity.toUser(): User{
        return User(
            email = email,
            password = password
        )
    }

    protected fun User.toUserEntity(): UserEntity{
        return UserEntity(
            email = email,
            password = password
        )
    }
}