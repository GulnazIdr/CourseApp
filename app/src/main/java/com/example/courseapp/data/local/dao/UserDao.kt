package com.example.courseapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.courseapp.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM userentity WHERE email=:id")
    suspend fun getUserById(id: String): UserEntity

    @Upsert
    fun addNewUser(userEntity: UserEntity)
}