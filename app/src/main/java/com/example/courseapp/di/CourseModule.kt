package com.example.courseapp.di

import com.example.courseapp.data.local.LocalCourseRepository
import com.example.courseapp.data.local.dao.CourseDao
import com.example.courseapp.data.remote.RemoteCourseRepositoryImpl
import com.example.courseapp.data.remote.retrofit.CourseAPI
import com.example.courseapp.domain.CourseRepository
import com.example.courseapp.domain.DataStoreRepository
import dagger.Module
import dagger.Provides

@Module
class CourseModule {
    @Provides
    fun provideCourseRepo(
        courseAPI: CourseAPI,
        courseDao: CourseDao,
        dataStoreRepository: DataStoreRepository
        ): CourseRepository{
        return RemoteCourseRepositoryImpl(courseAPI, courseDao, dataStoreRepository)
    }

    @Provides
    fun provideLocalCourseRepo(
        courseDao: CourseDao, dataStoreRepository: DataStoreRepository)
    : LocalCourseRepository{
        return LocalCourseRepository(courseDao, dataStoreRepository)
    }
}