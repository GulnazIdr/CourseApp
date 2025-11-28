package com.example.domain.domain

import com.example.common_feature.domain.models.Course
import kotlinx.coroutines.flow.MutableStateFlow

interface SearchCourseUseCase {
    suspend operator fun invoke(key: CharSequence?, courseList: List<Course>)
    : Pair<Boolean, List<Course>>
}