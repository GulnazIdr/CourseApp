package com.example.courseapp.data.mappers

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.courseapp.R
import com.example.courseapp.data.local.entity.CourseEntity
import com.example.courseapp.data.remote.retrofit.CourseDto
import com.example.courseapp.presentation.main.CourseMainInfo
import java.time.LocalDate

abstract class CourseMapper{

    @RequiresApi(Build.VERSION_CODES.O)
    protected fun CourseDto.toCourseUi(): CourseMainInfo{
        return CourseMainInfo(
            id = id ?: 0,
            title = title ?: "",
            descr = text ?: "",
            price = price!!.toIntOrNull() ?: 0,
            rate = rate!!.toFloatOrNull() ?: 0f,
            startDate = startDate!!.toLocalDate(),
            publishDate = publishDate!!.toLocalDate(),
            isFavorite = hasLike == true,
            img = R.drawable.java_image
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    protected fun CourseMainInfo.toCourseLocal(userId: String): CourseEntity{
        return CourseEntity(
            id = id,
            title = title ,
            descr = descr,
            price = price,
            rate = rate,
            startDate = startDate,
            publishDate = publishDate,
            isFavorite = isFavorite,
            img = img,
            userId = userId
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    protected fun CourseEntity.toCourseUI(): CourseMainInfo{
        return CourseMainInfo(
            id = id,
            title = title ,
            descr = descr,
            price = price,
            rate = rate,
            startDate = startDate,
            publishDate = publishDate,
            isFavorite = isFavorite,
            img = img
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun String.toLocalDate(): LocalDate{
        try {
            return LocalDate.parse(this)
        }catch (e: Exception){
            Log.e("Convert String to LocalDate failed: ", "${e.message} ${e::class.simpleName}")
            return LocalDate.now()
        }
    }
}