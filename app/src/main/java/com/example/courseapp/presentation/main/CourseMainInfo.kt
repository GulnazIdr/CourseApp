package com.example.courseapp.presentation.main

import java.time.LocalDate

data class CourseMainInfo(
    val id: Int,
    val title: String,
    val descr: String,
    val price: Float,
    val rate: Float,
    val startDate: LocalDate,
    val publishDate: LocalDate,
    val isFavorite: Boolean,
    val img: Int
)