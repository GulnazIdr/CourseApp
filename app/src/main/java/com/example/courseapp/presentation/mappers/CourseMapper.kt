package com.example.courseapp.presentation.mappers

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.courseapp.R
import com.example.courseapp.domain.models.Course
import com.example.courseapp.presentation.login.models.CourseUi
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
fun Course.toCourseUi(): CourseUi{
    return CourseUi(
        id = id ?: 0,
        title = title ?: "",
        descr = text ?: "",
        price = price ?: 0,
        rate = rate ?: 0f,
        startDate = startDate?.toLocalDate() ?: LocalDate.now(),
        publishDate = publishDate?.toLocalDate() ?: LocalDate.now(),
        isFavorite = hasLike == true,
        img = R.drawable.java_image
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun CourseUi.toCourse(): Course{
    return Course(
        id = id ,
        title = title,
        text = descr,
        price = price,
        rate = rate,
        startDate = startDate.toString(),
        publishDate = publishDate.toString(),
        hasLike = isFavorite
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
