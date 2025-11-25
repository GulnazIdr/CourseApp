package com.example.courseapp.domain

import com.example.courseapp.domain.models.Course

interface CourseRepository {
    suspend fun fetchCourses(): FetchedResult<List<Course>>
}