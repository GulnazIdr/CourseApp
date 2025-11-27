package com.example.common_feature.domain

import com.example.common_feature.domain.models.Course

interface CourseRepository {
    suspend fun fetchCourses(): FetchedResult<List<Course>>
}