package com.example.domain.domain

import com.example.common_feature.domain.models.Course

interface FetchCoursesUseCase {
    suspend operator fun invoke(): List<Course>
}