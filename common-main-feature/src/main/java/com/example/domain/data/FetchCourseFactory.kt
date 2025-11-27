package com.example.domain.data

import com.example.common_feature.domain.CourseRepository
import com.example.domain.data.local.LocalCourseRepositoryImpl
import com.example.domain.data.remote.RemoteCourseRepositoryImpl
import javax.inject.Inject

class FetchCourseFactory @Inject constructor(
    private val localCourseRepositoryImpl: LocalCourseRepositoryImpl,
    private val remoteCourseRepositoryImpl: RemoteCourseRepositoryImpl
){
    fun fetchCourse(courseRepoType: String): CourseRepository{
        return when(courseRepoType.lowercase()){
            "local" -> localCourseRepositoryImpl
            "remote" -> remoteCourseRepositoryImpl
            else -> throw IllegalArgumentException("Unknown element type")
        }
    }
}