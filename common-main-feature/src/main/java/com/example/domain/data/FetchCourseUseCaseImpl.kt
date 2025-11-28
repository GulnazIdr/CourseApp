package com.example.domain.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.common_feature.domain.DataStoreRepository
import com.example.common_feature.domain.FetchedResult
import com.example.common_feature.domain.models.Course
import com.example.domain.data.mappers.CourseMapper
import com.example.domain.domain.FetchCoursesUseCase
import com.example.domain.domain.LocalCourseRepository
import javax.inject.Inject

class FetchCourseUseCaseImpl @Inject constructor(
    private val fetchCourseFactory: FetchCourseFactory,
    private val localCourseRepository: LocalCourseRepository,
    private val dataStoreRepository: DataStoreRepository
): FetchCoursesUseCase, CourseMapper() {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun invoke(): List<Course> {
        val result = fetchCourseFactory.fetchCourse("remote").fetchCourses()
        when(result){
            is FetchedResult.Success<List<Course>> -> {
                val fetched =  result.data!!
                fetched.map { it.toCourseEntity(dataStoreRepository.getCurrentUserId()) }.forEach {
                    Log.d("CALLED2", "${localCourseRepository.isInLocalDb(it.id)}, ${it.id}")
                    if(!localCourseRepository.isInLocalDb(it.id)) {
                        localCourseRepository.saveCourses(it.toCourse())
                    }
                }
                return fetched
            }
            is FetchedResult.Error<List<Course>> -> {
                try {
                    Log.e("FETCHING REMOTE DB ERROR", "${result.errorMessage}")
                    return fetchCourseFactory
                        .fetchCourse("local")
                        .fetchCourses().data!!
                }catch (e: Exception){
                    Log.e("FETCHING LOCAL DB ERROR", "${e.message} ${e::class.simpleName}")
                    return emptyList()
                }
            }
        }
    }
}