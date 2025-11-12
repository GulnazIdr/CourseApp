package com.example.courseapp.di

import com.example.courseapp.LoginActivity
import com.example.courseapp.presentation.main.favorite.FavoriteFragment
import com.example.courseapp.presentation.main.home.HomeFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, CourseModule::class, UserModule::class])
interface AppComponent {
    fun inject(homeFragment: HomeFragment)
    fun inject(favoriteFragment: FavoriteFragment)
    fun inject(loginActivity: LoginActivity)
}