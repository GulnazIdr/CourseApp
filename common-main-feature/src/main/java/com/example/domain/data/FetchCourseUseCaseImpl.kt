package com.example.domain.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.common_feature.domain.FetchedResult
import com.example.common_feature.domain.UserRepository
import com.example.common_feature.domain.models.Course
import com.example.domain.data.mappers.CourseMapper
import com.example.domain.domain.LocalCourseRepository
import com.example.domain.domain.usecases.FetchCoursesUseCase
import javax.inject.Inject

class FetchCourseUseCaseImpl @Inject constructor(
    private val fetchCourseFactory: FetchCourseFactory,
    private val localCourseRepository: LocalCourseRepository,
    private val userRepository: UserRepository
): FetchCoursesUseCase, CourseMapper() {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun invoke(): List<Course> {
        val result = fetchCourseFactory.fetchCourse("remote").fetchCourses()
        val currentUserId: String? = userRepository.getCurrentUser()?.email

        when(result){
            is FetchedResult.Success<List<Course>> -> {
                val fetched =  result.data!!
                if(currentUserId != null)
                fetched.map { it.toCourseEntity(
                   currentUserId
                ) }.forEach {
                    if(!localCourseRepository.isInLocalDb(it.id)) {
                        localCourseRepository.saveCourses(it.toCourse())
                    }
                }
                return fetched
            }
            is FetchedResult.Error<List<Course>> -> {
                return try {
                    fetchCourseFactory
                        .fetchCourse("local")
                        .fetchCourses().data!!
                }catch (e: Exception){
                    emptyList()
                }
            }
        }
    }
}