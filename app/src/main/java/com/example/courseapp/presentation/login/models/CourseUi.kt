package com.example.courseapp.presentation.login.models

import java.time.LocalDate

data class CourseUi(
    val id: Int,
    val title: String,
    val descr: String,
    val price: Int,
    val rate: Float,
    val startDate: LocalDate,
    val publishDate: LocalDate,
    val isFavorite: Boolean,
    val img: Int
)
