package com.example.courseapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.courseapp.domain.CourseRepository
import javax.inject.Inject

class CourseMainVIewModelFactory @Inject constructor(
    internal val courseRepository: CourseRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CourseViewModel(courseRepository) as T
    }
}