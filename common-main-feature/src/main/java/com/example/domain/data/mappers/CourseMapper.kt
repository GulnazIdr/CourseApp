package com.example.domain.data.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.common_feature.data.entity.CourseEntity
import com.example.common_feature.domain.models.Course
import com.example.domain.data.remote.retrofit.models.CourseDto

abstract class CourseMapper {
    @RequiresApi(Build.VERSION_CODES.O)
    protected fun CourseDto.toCourse(isFavorite: Boolean): Course{
        return Course(
            id = id,
            title = title,
            text = text,
            price = price?.replace(" ", "")?.toIntOrNull(),
            rate = rate?.toFloatOrNull(),
            startDate = startDate,
            hasLike = isFavorite,
            publishDate = publishDate
        )
    }

    protected fun CourseEntity.toCourse(): Course{
        return Course(
            id = id,
            title = title,
            text = descr,
            price = price,
            rate = rate,
            startDate = startDate,
            hasLike = isFavorite,
            publishDate = publishDate
        )
    }

    protected fun Course.toCourseEntity(): CourseEntity{
        return CourseEntity(
            id = id!!,
            title =title!!,
            descr = text!!,
            price = price!!,
            rate = rate!!,
            startDate = startDate!!,
            publishDate = publishDate!!,
            isFavorite = hasLike!!,
            userId = "",
        )
    }
}