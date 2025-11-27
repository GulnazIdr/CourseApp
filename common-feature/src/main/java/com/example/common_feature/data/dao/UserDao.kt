package com.example.common_feature.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.common_feature.data.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM userentity WHERE email=:id")
    suspend fun getUserById(id: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewUser(userEntity: UserEntity)
}