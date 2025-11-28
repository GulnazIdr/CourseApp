package com.example.common_feature.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.common_feature.data.CourseDatabase
import com.example.common_feature.data.DataStoreRepoImpl
import com.example.common_feature.data.dao.CourseDao
import com.example.common_feature.data.dao.UserDao
import com.example.common_feature.domain.DataStoreRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule(val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideDatabase(context: Context): CourseDatabase{
        return Room.databaseBuilder(context, CourseDatabase::class.java, "course_db")
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideDataStore(): DataStore<Preferences>{
        return PreferenceDataStoreFactory.create(
            produceFile = {context.preferencesDataStoreFile("data_store")}
        )
    }

    @Provides
    fun provideDatastoreImpl(datastore: DataStore<Preferences>): DataStoreRepository{
        return DataStoreRepoImpl(datastore)
    }

    @Provides
    fun provideUserDao(courseDatabase: CourseDatabase): UserDao{
        return courseDatabase.getUserDao()
    }

    @Provides
    fun provideCourseDao(courseDatabase: CourseDatabase): CourseDao{
        return courseDatabase.getCourseDao()
    }

}