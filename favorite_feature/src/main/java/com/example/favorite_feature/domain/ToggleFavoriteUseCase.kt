package com.example.favorite_feature.domain

import com.example.common_feature.domain.FetchedResult
import com.example.common_feature.domain.models.Course

interface ToggleFavoriteUseCase {
    suspend operator fun invoke(courseId: Int): FetchedResult<List<Course>>
}