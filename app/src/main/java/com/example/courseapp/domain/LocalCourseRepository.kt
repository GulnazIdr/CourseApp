package com.example.courseapp.domain

import com.example.courseapp.domain.models.Course

interface LocalCourseRepository {
    suspend fun saveCourses(courseMainInfo: Course)
    suspend fun isInLocalDb(id: Int): Boolean
    suspend fun setFavoriteById(id: Int)
}