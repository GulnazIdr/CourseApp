package com.example.courseapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseapp.domain.CourseRepository
import com.example.courseapp.presentation.main.CourseMainInfo
import kotlinx.coroutines.launch
import javax.inject.Inject

class CourseViewModel @Inject constructor(
    internal val courseRepository: CourseRepository
): ViewModel() {

    var courseList: List<CourseMainInfo> = emptyList()

    init {
        fetchCourseList()
    }

    fun fetchCourseList(){
        viewModelScope.launch {
            courseList = courseRepository.fetchCourses()
        }
    }
}