package com.example.courseapp.presentation.mappers

import com.example.courseapp.domain.models.User
import com.example.courseapp.presentation.login.models.UserUi

fun UserUi.toUser(): User{
    return User(
        email = email,
        password = password
    )
}

fun User.toUserUI(): UserUi{
    return UserUi(
        email = email,
        password = password
    )
}
