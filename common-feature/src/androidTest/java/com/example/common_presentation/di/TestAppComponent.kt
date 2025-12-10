package com.example.common_presentation.di

import com.example.favorite_feature.presentation.fragments.FavoriteFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    DataModuleTest::class
])
interface TestAppComponent {
    fun inject(favoriteFragment: FavoriteFragment)
}