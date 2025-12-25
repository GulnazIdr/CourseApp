package com.example.user_feature.presentation.mappers

import com.example.common_feature.domain.models.User
import com.example.user_feature.presentation.models.UserUI

fun UserUI.toUser(): User{
    return User(
        email = email,
        password = password
    )
}