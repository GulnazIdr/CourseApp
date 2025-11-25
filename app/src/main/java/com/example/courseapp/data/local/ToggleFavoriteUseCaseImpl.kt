package com.example.courseapp.data.local

import android.util.Log
import com.example.courseapp.data.FetchCourseFactory
import com.example.courseapp.data.mappers.CourseMapper
import com.example.courseapp.domain.FetchedResult
import com.example.courseapp.domain.LocalCourseRepository
import com.example.courseapp.domain.models.Course
import com.example.courseapp.domain.usecases.ToggleFavoriteUseCase
import javax.inject.Inject

class ToggleFavoriteUseCaseImpl @Inject constructor(
    private val localCourseRepository: LocalCourseRepository,
    private val fetchCourseFactory: FetchCourseFactory
): ToggleFavoriteUseCase, CourseMapper()  {
    override suspend fun invoke(courseId: Int): FetchedResult<List<Course>> {
        try {
            localCourseRepository.setFavoriteById(courseId)
            return FetchedResult.Success(
                fetchCourseFactory.fetchCourse("local").fetchCourses().data!!
            )
        }catch (e: Exception){
            Log.e("FETCHING LOCAL DB ERROR", "${e.message} ${e::class.simpleName}")
            return FetchedResult.Error(e.message.toString());
        }
    }
}