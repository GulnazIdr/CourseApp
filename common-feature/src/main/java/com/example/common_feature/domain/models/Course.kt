package com.example.common_feature.domain.models

data class Course(
    val id: Int? = null,
    val title: String? = null,
    val text: String? = null,
    val price: Int? = null,
    val rate: Float? = null,
    val startDate: String? = null,
    val hasLike: Boolean? = null,
    val publishDate: String? = null
)
