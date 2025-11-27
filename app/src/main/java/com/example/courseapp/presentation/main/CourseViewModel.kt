package com.example.courseapp.presentation.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common_feature.domain.FetchedResult
import com.example.common_feature.domain.models.Course
import com.example.courseapp.presentation.mappers.toCourseUi
import com.example.domain.domain.FetchCoursesUseCase
import com.example.domain.presentation.models.CourseUi
import com.example.favorite_feature.domain.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class CourseViewModel @Inject constructor(
    internal val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    internal val fetchCoursesUseCase: FetchCoursesUseCase,
): ViewModel() {
    private val _courseList = MutableStateFlow<List<CourseUi>>(emptyList())
    val courseList: StateFlow<List<CourseUi>> = _courseList.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        fetchCourseList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchCourseList() =
        viewModelScope.launch {
            _isLoading.value = true
            _courseList.value = fetchCoursesUseCase().map { it.toCourseUi() }
            _isLoading.value = false

        }

    fun onFavorite(courseId: Int) {
        viewModelScope.launch {
            val res = toggleFavoriteUseCase(courseId)
            when(res){
                is FetchedResult.Success<List<Course>> ->
                    _courseList.value = res.data!!.map { it.toCourseUi() }
                is FetchedResult.Error<List<Course>> -> {}
            }
        }
    }

    fun sortByPublishDate(){
        _courseList.value = _courseList.value.sortedByDescending { it.publishDate }
    }

}