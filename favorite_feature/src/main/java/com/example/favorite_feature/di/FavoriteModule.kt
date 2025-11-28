package com.example.favorite_feature.di

import com.example.domain.data.FetchCourseFactory
import com.example.domain.domain.LocalCourseRepository
import com.example.favorite_feature.data.ToggleFavoriteUseCaseImpl
import com.example.favorite_feature.domain.ToggleFavoriteUseCase
import dagger.Module
import dagger.Provides

@Module
class FavoriteModule {
    @Provides
    fun provideToggleFavorite(
        localCourseRepository: LocalCourseRepository,
        fetchCourseFactory: FetchCourseFactory
    ): ToggleFavoriteUseCase{
        return ToggleFavoriteUseCaseImpl(localCourseRepository, fetchCourseFactory)
    }
}