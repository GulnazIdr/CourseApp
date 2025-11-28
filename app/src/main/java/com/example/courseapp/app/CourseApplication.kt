package com.example.courseapp.app

import android.app.Application
import com.example.common_feature.di.DataModule
import com.example.courseapp.di.AppComponent
import com.example.courseapp.di.DaggerAppComponent
import com.example.favorite_feature.di.CommonFeatureComponent
import com.example.favorite_feature.presentation.fragments.FavoriteFragment

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