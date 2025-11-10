package com.example.courseapp.di

import com.example.courseapp.presentation.main.favorite.FavoriteFragment
import com.example.courseapp.presentation.main.home.HomeFragment
import dagger.Component

@Component(modules = [DataModule::class])
interface AppComponent {
    fun inject(homeFragment: HomeFragment)
    fun inject(favoriteFragment: FavoriteFragment)
}