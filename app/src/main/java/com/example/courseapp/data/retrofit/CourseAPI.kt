package com.example.courseapp.data.retrofit

import retrofit2.http.GET

interface CourseAPI {
    @GET("courses")
    suspend fun fetchCourses(): List<CourseDto>
}