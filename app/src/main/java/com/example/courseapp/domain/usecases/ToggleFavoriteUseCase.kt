package com.example.courseapp.domain.usecases

import com.example.courseapp.domain.FetchedResult
import com.example.courseapp.domain.models.Course

interface ToggleFavoriteUseCase {
    suspend operator fun invoke(courseId: Int): FetchedResult<List<Course>>
}