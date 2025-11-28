package com.example.favorite_feature.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.domain.usecases.FetchCoursesUseCase
import com.example.favorite_feature.domain.ToggleFavoriteUseCase
import javax.inject.Inject

class FavoriteVIewModelFactory @Inject constructor(
    internal val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    internal val fetchCoursesUseCase: FetchCoursesUseCase,
): ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteViewModel(toggleFavoriteUseCase, fetchCoursesUseCase) as T
    }
}