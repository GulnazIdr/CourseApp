package com.example.courseapp.data.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.courseapp.data.local.entity.CourseEntity
import com.example.courseapp.data.remote.retrofit.models.CourseDto
import com.example.courseapp.domain.models.Course

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