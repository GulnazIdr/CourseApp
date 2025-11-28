package com.example.courseapp.di

import com.example.common_feature.di.DataModule
import com.example.common_feature.di.UserModule
import com.example.courseapp.presentation.login.LoginActivity
import com.example.courseapp.presentation.main.home.HomeFragment
import com.example.domain.di.CourseModule
import com.example.domain.di.RetrofitModule
import com.example.favorite_feature.di.FavoriteModule
import com.example.favorite_feature.presentation.fragments.FavoriteFragment
import com.example.user_feature.di.AuthModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    DataModule::class, CourseModule::class, UserModule::class,
    RetrofitModule::class, FavoriteModule::class, AuthModule::class
])
interface AppComponent {
    fun inject(homeFragment: HomeFragment)
    fun inject(favoriteFragment: FavoriteFragment)
    fun inject(loginActivity: LoginActivity)
}