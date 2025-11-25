package com.example.courseapp.data

import com.example.courseapp.data.local.LocalCourseRepositoryImpl
import com.example.courseapp.data.remote.RemoteCourseRepositoryImpl
import com.example.courseapp.domain.CourseRepository
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