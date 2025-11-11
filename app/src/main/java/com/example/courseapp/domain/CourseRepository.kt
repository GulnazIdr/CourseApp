package com.example.courseapp.domain

import com.example.courseapp.presentation.main.CourseMainInfo

interface CourseRepository {
    suspend fun fetchCourses(): FetchedResult<List<CourseMainInfo>>
}