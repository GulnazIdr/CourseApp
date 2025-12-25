package com.example.common_feature.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.common_feature.data.dao.CourseDao
import com.example.common_feature.data.dao.UserDao
import com.example.common_feature.data.entity.CourseEntity
import com.example.common_feature.data.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        CourseEntity::class
    ],
    version = 2,
    exportSchema = true,
    autoMigrations = []
)

abstract class CourseDatabase: RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getCourseDao(): CourseDao
}