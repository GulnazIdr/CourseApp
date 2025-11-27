package com.example.common_feature.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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
    version = 1,
    exportSchema = false
)

abstract class CourseDatabase: RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getCourseDao(): CourseDao

    companion object{
        @Volatile
        private var INSTANCE: CourseDatabase? = null

        fun getDatabase(context: Context): CourseDatabase{
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, CourseDatabase::class.java, "course_db")
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}