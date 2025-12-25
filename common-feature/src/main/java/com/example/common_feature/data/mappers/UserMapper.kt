package com.example.common_feature.data.mappers

import com.example.common_feature.data.datastore.UserSerial
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

    protected fun UserSerial.toUser(): User{
        return User(
            email = email,
            password = password
        )
    }

    protected fun User.toUserSerial(): UserSerial{
        return UserSerial(
            email = email,
            password = password
        )
    }
}