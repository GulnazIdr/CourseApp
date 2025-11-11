package com.example.courseapp.presentation.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.courseapp.data.local.LocalCourseRepository
import com.example.courseapp.data.local.dao.CourseDao
import com.example.courseapp.domain.CourseRepository
import okhttp3.internal.Internal
import javax.inject.Inject

class CourseMainVIewModelFactory @Inject constructor(
    internal val courseRepository: CourseRepository,
    internal val localCourseRepository: LocalCourseRepository
): ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CourseViewModel(courseRepository, localCourseRepository) as T
    }
}