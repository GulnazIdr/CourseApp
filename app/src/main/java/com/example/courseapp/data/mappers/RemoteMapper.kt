package com.example.courseapp.data.mappers

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.courseapp.R
import com.example.courseapp.data.retrofit.CourseDto
import com.example.courseapp.presentation.classes.CourseMainInfo
import java.time.LocalDate

abstract class RemoteMapper{

    @RequiresApi(Build.VERSION_CODES.O)
    protected fun CourseDto.toCourseUi(): CourseMainInfo{
        return CourseMainInfo(
            id = id,
            title = title,
            descr = text,
            price = price.toFloatOrNull() ?: 0f,
            rate = rate.toFloatOrNull() ?: 0f,
            startDate = startDate.toLocalDate(),
            publishDate = publishDate.toLocalDate(),
            isFavorite = hasLike,
            img = R.drawable.java_image
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