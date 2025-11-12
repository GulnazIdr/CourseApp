package com.example.courseapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.courseapp.data.remote.retrofit.CourseAPI
import com.example.courseapp.data.remote.RemoteCourseRepositoryImpl
import com.example.courseapp.data.local.CourseDatabase
import com.example.courseapp.data.local.DataStoreRepoImpl
import com.example.courseapp.data.local.LocalCourseRepository
import com.example.courseapp.data.local.LocalUserRepository
import com.example.courseapp.data.local.dao.CourseDao
import com.example.courseapp.data.local.dao.UserDao
import com.example.courseapp.domain.CourseRepository
import com.example.courseapp.domain.DataStoreRepository
import com.example.courseapp.presentation.login.AuthViewModelFactory
import com.example.courseapp.presentation.main.CourseCardStateAdapter
import com.example.courseapp.presentation.main.CourseMainVIewModelFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule(val context: Context) {

    @Provides
    fun provideContext(): Context{
        return context
    }

    @Provides
    fun provideCourseViewModelFactory(
        courseRepository: CourseRepository,
        localCourseRepository: LocalCourseRepository
        ): CourseMainVIewModelFactory{
        return CourseMainVIewModelFactory(
            courseRepository, localCourseRepository)
    }

    @Provides
    fun provideAuthViewModelFactory(
       localUserRepository: LocalUserRepository,
       dataStoreRepository: DataStoreRepository
    ): AuthViewModelFactory{
        return AuthViewModelFactory(localUserRepository, dataStoreRepository)
    }

    @Provides
    fun provideRetrofit(): CourseAPI{
        return Retrofit.Builder()
            .baseUrl("https://drive.usercontent.google.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CourseAPI::class.java)
    }

    @Provides
    fun provideDatabase(context: Context): CourseDatabase{
        return Room.databaseBuilder(context, CourseDatabase::class.java, "course_db")
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