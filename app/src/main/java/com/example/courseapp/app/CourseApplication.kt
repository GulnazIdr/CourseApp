package com.example.courseapp.app

import android.app.Application
import com.example.courseapp.di.AppComponent
import com.example.courseapp.di.DaggerAppComponent

class CourseApplication: Application(){
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

         appComponent = DaggerAppComponent.create()
    }
}