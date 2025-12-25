package com.example.common_feature.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.common_feature.data.CourseDatabase
import com.example.common_feature.data.dao.CourseDao
import com.example.common_feature.data.dao.UserDao
import com.example.common_feature.data.datastore.CurrentUserSerializer
import com.example.common_feature.data.datastore.DataStoreRepoImpl
import com.example.common_feature.data.datastore.UserPreferencesSerializer
import com.example.common_feature.data.datastore.UserSerial
import com.example.common_feature.domain.DataStoreRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
class DataModule(val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideDatabase(context: Context): CourseDatabase{
        return Room.databaseBuilder(context, CourseDatabase::class.java, "course_db")
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Singleton
    @Provides
    fun provideDataStore(): DataStore<Preferences>{
        return PreferenceDataStoreFactory.create(
            produceFile = {context.preferencesDataStoreFile("data_store")}
        )
    }

    @Singleton
    @Provides
    fun provideUserPreferencesDataStore(): DataStore<List<UserSerial>>{
        return DataStoreFactory.create(
            serializer = UserPreferencesSerializer,
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyList<UserSerial>() }
            ),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = {context.dataStoreFile("USER_CREDENTIALS")}
        )
    }

    @Singleton
    @Provides
    fun provideCurrentUserDataStore(): DataStore<UserSerial>{
        return DataStoreFactory.create(
            serializer = CurrentUserSerializer,
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { UserSerial("", "") }
            ),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = {context.dataStoreFile("CURRENT_USER")}
        )
    }

    @Singleton
    @Provides
    fun provideDatastoreImpl(
        datastore: DataStore<Preferences>,
        userDataStorePref: DataStore<List<UserSerial>>,
        currentUserDataStorePref: DataStore<UserSerial>
    ): DataStoreRepository{
        return DataStoreRepoImpl(datastore, userDataStorePref, currentUserDataStorePref)
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