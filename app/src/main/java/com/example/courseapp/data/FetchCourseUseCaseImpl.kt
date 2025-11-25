package com.example.courseapp.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.courseapp.data.local.LocalCourseRepositoryImpl
import com.example.courseapp.data.mappers.CourseMapper
import com.example.courseapp.data.remote.RemoteCourseRepositoryImpl
import com.example.courseapp.domain.FetchedResult
import com.example.courseapp.domain.LocalCourseRepository
import com.example.courseapp.domain.models.Course
import com.example.courseapp.domain.usecases.FetchCoursesUseCase
import javax.inject.Inject

class FetchCourseUseCaseImpl @Inject constructor(
    private val fetchCourseFactory: FetchCourseFactory,
    private val localCourseRepository: LocalCourseRepository
): FetchCoursesUseCase, CourseMapper() {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun invoke(): List<Course> {
        val result = fetchCourseFactory.fetchCourse("remote").fetchCourses()
        when(result){
            is FetchedResult.Success<List<Course>> -> {
                val fetched =  result.data!!

                fetched.map { it.toCourseEntity() }.forEach {
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