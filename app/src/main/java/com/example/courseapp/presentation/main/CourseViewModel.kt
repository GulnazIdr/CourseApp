package com.example.courseapp.presentation.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common_feature.domain.FetchedResult
import com.example.common_feature.domain.models.Course
import com.example.domain.domain.FetchCoursesUseCase
import com.example.domain.domain.SearchCourseUseCase
import com.example.domain.presentation.mappers.toCourse
import com.example.domain.presentation.mappers.toCourseUi
import com.example.domain.presentation.models.CourseUi
import com.example.favorite_feature.domain.ToggleFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class CourseViewModel @Inject constructor(
    internal val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    internal val fetchCoursesUseCase: FetchCoursesUseCase,
    internal val searchCourseUseCase: SearchCourseUseCase
): ViewModel() {
    private val _courseList = MutableStateFlow<List<CourseUi>>(emptyList())
    val courseList: StateFlow<List<CourseUi>> = _courseList.asStateFlow()

    private val _filteredCourseList = MutableStateFlow<List<CourseUi>>(emptyList())
    val filteredCourseList: StateFlow<List<CourseUi>> = _filteredCourseList.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isSearching = MutableStateFlow<Boolean>(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

    private val _currentSearchKey = MutableStateFlow<CharSequence?>("")

    init {
        fetchCourseList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchCourseList() =
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            _courseList.value = fetchCoursesUseCase().map { it.toCourseUi() }
            _isLoading.value = false
        }

    fun onFavorite(courseId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val res = toggleFavoriteUseCase(courseId)
            when(res){
                is FetchedResult.Success<List<Course>> ->
                    if (_isSearching.value)
                        _filteredCourseList.value = searchCourseUseCase(
                            _currentSearchKey.value, res.data!!
                        ).second.map { it.toCourseUi() }
                    else
                    _courseList.value = res.data!!.map { it.toCourseUi() }
                is FetchedResult.Error<List<Course>> -> {}
            }
        }
    }

    fun sortByPublishDate(){
        _courseList.value = _courseList.value.sortedByDescending { it.publishDate }
    }

    fun searchByKeyword(item: CharSequence?){
        _currentSearchKey.value = item
        var pair: Pair<Boolean, List<Course>>
        viewModelScope.launch(Dispatchers.Main) {
             pair = searchCourseUseCase(item, _courseList.value.map { it.toCourse() })
            _isSearching.value = pair.first
            _filteredCourseList.value = pair.second.map { it.toCourseUi() }
        }
    }

}