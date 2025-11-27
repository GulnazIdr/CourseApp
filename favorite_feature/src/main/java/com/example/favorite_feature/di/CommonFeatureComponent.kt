package com.example.favorite_feature.di

import com.example.favorite_feature.presentation.FavoriteFragment

interface CommonFeatureComponent {
    fun injectCommonFeature(favoriteFragment: FavoriteFragment)
}