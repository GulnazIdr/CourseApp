package com.example.domain.domain

import com.example.common_feature.domain.models.Course
import com.example.domain.domain.usecases.SearchCourseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class SearchCourseUseCaseImpl @Inject constructor(

): SearchCourseUseCase {
    override suspend fun invoke(key: CharSequence?, courseList: List<Course>)
    : Pair<Boolean, List<Course>> {
        val isSearching = MutableStateFlow<Boolean>(false)
        val filteredCourseList = MutableStateFlow<List<Course>>(emptyList())
        if (!key.isNullOrEmpty()) {
            isSearching.value = true
            filteredCourseList.value = courseList.filter {
                it.title!!.contains(key.toString().replace(" ", ""), ignoreCase = true) ||
                        it.text!!.contains(key.toString().replace(" ", ""), ignoreCase = true)
            }
        } else {
            isSearching.value = false
            filteredCourseList.value = courseList
        }
        return Pair(isSearching.value, filteredCourseList.value)
    }
}