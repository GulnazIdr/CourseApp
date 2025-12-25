package com.example.common_feature.data.datastore


import kotlinx.serialization.Serializable

@Serializable
data class UserSerial(
    val email: String,
    val password: String
)
