package com.example.courseapp.presentation.classes

import java.time.LocalDate

data class CourseMainInfo(
    val id: Int,
    val title: String,
    val descr: String,
    val price: Float,
    val rate: Float,
    val date: LocalDate,
    val isFavoirite: Boolean,
    val img: String
)