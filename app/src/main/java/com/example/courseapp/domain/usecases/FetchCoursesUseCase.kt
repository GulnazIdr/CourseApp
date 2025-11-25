package com.example.courseapp.domain.usecases

import com.example.courseapp.domain.models.Course

interface FetchCoursesUseCase {
    suspend operator fun invoke(): List<Course>
}