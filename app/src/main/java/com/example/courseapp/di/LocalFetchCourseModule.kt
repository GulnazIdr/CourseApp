package com.example.courseapp.di

import com.example.courseapp.data.local.LocalCourseRepositoryImpl
import com.example.courseapp.data.remote.RemoteCourseRepositoryImpl
import com.example.courseapp.domain.CourseRepository
import com.example.courseapp.domain.LocalCourseRepository
import dagger.Binds
import dagger.Module
import javax.inject.Named

@Module
abstract class LocalFetchCourseModule {
    @Binds
    abstract fun provideLocalRepo(
        localCourseRepositoryImpl: LocalCourseRepositoryImpl): LocalCourseRepository

    @Binds
    @Named("Remote")
    abstract fun bindRemote(repo: RemoteCourseRepositoryImpl): CourseRepository

    @Binds
    @Named("Local")
    abstract fun bindLocal(repo: LocalCourseRepositoryImpl): CourseRepository
}