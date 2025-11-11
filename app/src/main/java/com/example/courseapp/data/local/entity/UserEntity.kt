package com.example.courseapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey val email: String,
    val password: String
)
