package com.example.courseapp.data.retrofit

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.courseapp.data.mappers.RemoteMapper
import com.example.courseapp.domain.CourseRepository
import com.example.courseapp.presentation.main.CourseMainInfo
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val courseAPI: CourseAPI
): CourseRepository, RemoteMapper() {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun fetchCourses(): List<CourseMainInfo> {
        val fetched = courseAPI.fetchCourses()

        return fetched.map { it.toCourseUi() }
    }
}