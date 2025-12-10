package com.example.common_presentation.di

import android.content.Context
import androidx.room.Room
import com.example.common_feature.data.CourseDatabase
import com.example.common_feature.data.dao.CourseDao
import com.example.common_feature.data.dao.UserDao
import dagger.Module
import dagger.Provides

@Module
class DataModuleTest(val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideDatabase(context: Context): CourseDatabase {
        return Room.inMemoryDatabaseBuilder(
            context,
            CourseDatabase::class.java
        ).build()
    }

    @Provides
    fun provideUserDao(courseDatabase: CourseDatabase): UserDao {
        return courseDatabase.getUserDao()
    }

    @Provides
    fun provideCourseDao(courseDatabase: CourseDatabase): CourseDao {
        return courseDatabase.getCourseDao()
    }
}