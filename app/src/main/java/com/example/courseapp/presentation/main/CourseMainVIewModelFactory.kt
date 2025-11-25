package com.example.courseapp.presentation.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.courseapp.data.local.LocalCourseRepositoryImpl
import com.example.courseapp.domain.CourseRepository
import com.example.courseapp.domain.usecases.FetchCoursesUseCase
import com.example.courseapp.domain.usecases.ToggleFavoriteUseCase
import javax.inject.Inject

class CourseMainVIewModelFactory @Inject constructor(
    internal val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    internal val fetchCoursesUseCase: FetchCoursesUseCase,
): ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CourseViewModel(toggleFavoriteUseCase, fetchCoursesUseCase) as T
    }
}