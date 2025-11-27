package com.example.courseapp.app

import android.app.Application
import com.example.courseapp.di.AppComponent
import com.example.courseapp.di.CourseModule
import com.example.courseapp.di.DaggerAppComponent
import com.example.courseapp.di.DataModule
import com.example.favorite_feature.di.CommonFeatureComponent
import com.example.favorite_feature.presentation.FavoriteFragment

class CourseApplication: Application(), CommonFeatureComponent{
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .dataModule(DataModule(context = this))
            .build()
    }

    override fun injectCommonFeature(favoriteFragment: FavoriteFragment) {
        appComponent.inject(favoriteFragment)
    }
}