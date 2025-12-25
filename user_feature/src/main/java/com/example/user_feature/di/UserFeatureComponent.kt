package com.example.user_feature.di

import com.example.user_feature.presentation.MyAutofillService

interface UserFeatureComponent {
    fun injectAutofillService(autofillService: MyAutofillService)
}