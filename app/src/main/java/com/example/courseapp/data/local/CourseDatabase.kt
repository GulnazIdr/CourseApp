package com.example.courseapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.courseapp.data.local.dao.CourseDao
import com.example.courseapp.data.local.dao.UserDao
import com.example.courseapp.data.local.entity.CourseEntity
import com.example.courseapp.data.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        CourseEntity::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(TimeConverter::class)

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