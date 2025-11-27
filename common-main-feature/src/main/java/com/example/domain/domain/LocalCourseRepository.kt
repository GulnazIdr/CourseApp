package com.example.domain.domain

import com.example.common_feature.domain.models.Course

interface LocalCourseRepository {
    suspend fun saveCourses(courseMainInfo: Course)
    suspend fun isInLocalDb(id: Int): Boolean
    suspend fun setFavoriteById(id: Int)
}