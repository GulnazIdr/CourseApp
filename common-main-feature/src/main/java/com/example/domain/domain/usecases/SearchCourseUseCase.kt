package com.example.domain.domain.usecases

import com.example.common_feature.domain.models.Course

interface SearchCourseUseCase {
    suspend operator fun invoke(key: CharSequence?, courseList: List<Course>)
    : Pair<Boolean, List<Course>>
}