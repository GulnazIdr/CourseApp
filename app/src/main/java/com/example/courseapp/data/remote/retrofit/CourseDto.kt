package com.example.courseapp.data.remote.retrofit

data class CourseDto(
    val id: Int? = null,
    val title: String? = null,
    val text: String? = null,
    val price: String? = null,
    val rate: String? = null,
    val startDate: String? = null,
    val hasLike: Boolean? = null,
    val publishDate: String? = null
)