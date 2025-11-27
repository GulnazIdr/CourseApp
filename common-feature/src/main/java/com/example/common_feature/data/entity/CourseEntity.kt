package com.example.common_feature.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CourseEntity(
    val id: Int,
    val title: String,
    val descr: String,
    val price: Int,
    val rate: Float,
    val startDate: String,
    val publishDate: String,
    val isFavorite: Boolean,
    val userId: String,
    @PrimaryKey(autoGenerate = true) val courseId: Int = 0,
)