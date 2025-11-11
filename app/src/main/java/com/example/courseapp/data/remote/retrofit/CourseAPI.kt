package com.example.courseapp.data.remote.retrofit

import retrofit2.http.GET
import retrofit2.http.Url

interface CourseAPI {
    @GET
    suspend fun fetchCourses(
        @Url url: String = "https://drive.usercontent.google.com/u/0/uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download/"
    ): CourseListDto
}