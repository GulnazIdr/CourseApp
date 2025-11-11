package com.example.courseapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class CourseEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val descr: String,
    val price: Int,
    val rate: Float,
    val startDate: LocalDate,
    val publishDate: LocalDate,
    val isFavorite: Boolean,
    val img: Int,
    val userId: String
)
