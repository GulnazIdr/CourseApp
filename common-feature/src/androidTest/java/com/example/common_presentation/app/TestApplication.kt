package com.example.common_presentation.app

import android.app.Application
import com.example.common_presentation.di.TestAppComponent

class TestApplication: Application() {
    lateinit var testAppComponent: TestAppComponent

    override fun onCreate() {
        super.onCreate()


    }
}