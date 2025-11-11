package com.example.courseapp.presentation.main

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseapp.data.local.LocalCourseRepository
import com.example.courseapp.domain.CourseRepository
import com.example.courseapp.domain.DataStoreRepository
import com.example.courseapp.domain.FetchedResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class CourseViewModel @Inject constructor(
    internal val courseRepository: CourseRepository,
    internal val localCourseRepository: LocalCourseRepository
): ViewModel() {

    private val _courseList = MutableStateFlow<List<CourseMainInfo>>(emptyList())
    val courseList: StateFlow<List<CourseMainInfo>> = _courseList.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        fetchCourseList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchCourseList() =
        viewModelScope.launch {
            _isLoading.value = true
            val result = courseRepository.fetchCourses()

            when(result){
                is FetchedResult.Success<List<CourseMainInfo>> -> {
                    _courseList.value = result.data!!.toMutableList()

                    _courseList.value.forEach {
                        if(localCourseRepository.isInLocalDb(it.id))
                            localCourseRepository.saveCourses(it)
                    }
                }
                is FetchedResult.Error<List<CourseMainInfo>> -> {
                    try {
                        _courseList.value = localCourseRepository.fetchCourses()
                    }catch (e: Exception){
                        Log.e("FETCHING LOCAL DB ERROR", "${e.message} ${e::class.simpleName}")
                    }
                }
            }

            _isLoading.value = false

        }

    fun setFavorite(id: Int, isFavorite: Boolean) = viewModelScope.launch {
        localCourseRepository.setFavoriteById(id, isFavorite)
    }

}