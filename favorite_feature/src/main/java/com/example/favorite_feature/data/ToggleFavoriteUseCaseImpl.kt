package com.example.favorite_feature.data

import android.util.Log
import com.example.common_feature.domain.FetchedResult
import com.example.common_feature.domain.models.Course
import com.example.domain.data.FetchCourseFactory
import com.example.domain.data.mappers.CourseMapper
import com.example.domain.domain.LocalCourseRepository
import com.example.favorite_feature.domain.ToggleFavoriteUseCase
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