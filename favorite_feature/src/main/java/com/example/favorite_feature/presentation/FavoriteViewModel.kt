package com.example.favorite_feature.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common_feature.domain.FetchedResult
import com.example.common_feature.domain.models.Course
import com.example.domain.domain.usecases.FetchCoursesUseCase
import com.example.domain.presentation.models.CourseUi
import com.example.domain.presentation.mappers.toCourseUi
import com.example.favorite_feature.domain.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class FavoriteViewModel @Inject constructor(
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
}