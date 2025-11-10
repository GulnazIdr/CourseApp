package com.example.courseapp.di

import com.example.courseapp.data.retrofit.CourseAPI
import com.example.courseapp.data.retrofit.CourseRepositoryImpl
import com.example.courseapp.domain.CourseRepository
import com.example.courseapp.presentation.main.CourseMainVIewModelFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class DataModule() {

    @Provides
    fun provideCourseRepo(courseAPI: CourseAPI): CourseRepository{
        return CourseRepositoryImpl(courseAPI)
    }

    @Provides
    fun provideCourseViewModelFactory(courseRepository: CourseRepository): CourseMainVIewModelFactory{
        return CourseMainVIewModelFactory(courseRepository)
    }

    @Provides
    fun provideRetrofit(): CourseAPI{
        return Retrofit.Builder()
            .baseUrl("http://localhost:3000/courses/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CourseAPI::class.java)
    }
}