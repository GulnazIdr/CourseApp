package com.example.domain.di

import com.example.common_feature.data.dao.CourseDao
import com.example.common_feature.domain.DataStoreRepository
import com.example.domain.data.FetchCourseFactory
import com.example.domain.data.FetchCourseUseCaseImpl
import com.example.domain.domain.SearchCourseUseCaseImpl
import com.example.domain.data.local.LocalCourseRepositoryImpl
import com.example.domain.data.remote.RemoteCourseRepositoryImpl
import com.example.domain.data.remote.retrofit.CourseAPI
import com.example.domain.domain.usecases.FetchCoursesUseCase
import com.example.domain.domain.LocalCourseRepository
import com.example.domain.domain.usecases.SearchCourseUseCase
import dagger.Module
import dagger.Provides

@Module
class CourseModule {
    @Provides
    fun provideFetchCourseUseCase(
        localCourseRepository: LocalCourseRepository,
        fetchCourseFactory: FetchCourseFactory,
        dataStoreRepository: DataStoreRepository
    ): FetchCoursesUseCase = FetchCourseUseCaseImpl(
        fetchCourseFactory, localCourseRepository, dataStoreRepository)

    @Provides
    fun provideSearch(): SearchCourseUseCase{
        return SearchCourseUseCaseImpl()
    }

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
            : LocalCourseRepository{
        return LocalCourseRepositoryImpl(courseDao, dataStoreRepository)
    }
}