package com.example.common_feature.data.mappers

import com.example.common_feature.data.entity.UserEntity
import com.example.common_feature.domain.models.User

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