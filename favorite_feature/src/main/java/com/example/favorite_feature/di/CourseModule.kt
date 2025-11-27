package com.example.favorite_feature.di

import com.example.courseapp.data.FetchCourseFactory
import com.example.courseapp.data.FetchCourseUseCaseImpl
import com.example.courseapp.data.local.LocalCourseRepositoryImpl
import com.example.courseapp.data.local.ToggleFavoriteUseCaseImpl
import com.example.courseapp.data.local.dao.CourseDao
import com.example.courseapp.data.remote.RemoteCourseRepositoryImpl
import com.example.courseapp.data.remote.retrofit.CourseAPI
import com.example.courseapp.domain.DataStoreRepository
import com.example.courseapp.domain.LocalCourseRepository
import com.example.courseapp.domain.usecases.FetchCoursesUseCase
import com.example.courseapp.domain.usecases.ToggleFavoriteUseCase
import dagger.Module
import dagger.Provides

@Module
class CourseModule {
    @Provides
    fun provideRemoteCourseRepo(
        courseAPI: CourseAPI,
        courseDao: CourseDao,
        dataStoreRepository: DataStoreRepository
        ): RemoteCourseRepositoryImpl{
        return RemoteCourseRepositoryImpl(courseAPI, courseDao, dataStoreRepository)
    }

    @Provides
    fun provideLocalCourseRepoImpl(
        courseDao: CourseDao, dataStoreRepository: DataStoreRepository)
    : LocalCourseRepositoryImpl{
        return LocalCourseRepositoryImpl(courseDao, dataStoreRepository)
    }

    @Provides
    fun provideFetchCourseFactory(
        localCourseRepositoryImpl: LocalCourseRepositoryImpl,
        remoteCourseRepositoryImpl: RemoteCourseRepositoryImpl
    ): FetchCourseFactory{
        return FetchCourseFactory(localCourseRepositoryImpl, remoteCourseRepositoryImpl)
    }

    @Provides
    fun provideToggleFavorite(
        localCourseRepository: LocalCourseRepository,
        fetchCourseFactory: FetchCourseFactory
    ): ToggleFavoriteUseCase{
        return ToggleFavoriteUseCaseImpl(localCourseRepository, fetchCourseFactory)
    }

    @Provides
    fun provideFetchCourseUseCase(
        localCourseRepository: LocalCourseRepository,
        fetchCourseFactory: FetchCourseFactory
    ): FetchCoursesUseCase = FetchCourseUseCaseImpl(fetchCourseFactory, localCourseRepository)
}