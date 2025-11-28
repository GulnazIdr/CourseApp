package com.example.domain.di

import com.example.domain.data.remote.retrofit.CourseAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class RetrofitModule() {
    @Provides
    fun provideRetrofit(): CourseAPI{
        return Retrofit.Builder()
            .baseUrl("https://drive.usercontent.google.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CourseAPI::class.java)
    }
}