package com.example.courseapp.data.local

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.courseapp.data.local.dao.CourseDao
import com.example.courseapp.data.local.entity.CourseEntity
import com.example.courseapp.data.mappers.CourseMapper
import com.example.courseapp.domain.DataStoreRepository
import com.example.courseapp.presentation.main.CourseMainInfo
import javax.inject.Inject

class LocalCourseRepository @Inject constructor(
    internal val courseDao: CourseDao,
    internal val dataStoreRepository: DataStoreRepository
):CourseMapper() {

    @RequiresApi(Build.VERSION_CODES.O)
     suspend fun fetchCourses(): List<CourseMainInfo> {
        return courseDao.fetchCourses().map { it.toCourseUI() }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveCourses(courseMainInfo: CourseMainInfo){
        courseDao.saveCourse(courseMainInfo.toCourseLocal(
            userId = dataStoreRepository.getCurrentUserId()
        ))
    }

    suspend fun isInLocalDb(id: Int): Boolean{
        return courseDao.getCourseById(id, dataStoreRepository.getCurrentUserId()) != null
    }

    suspend fun setFavoriteById(id: Int, isFavorite: Boolean){
        val course = courseDao.getCourseById(id, dataStoreRepository.getCurrentUserId())!!

        courseDao.setFavoriteById(CourseEntity(
            id, course.title, course.descr, course.price, course.rate, course.startDate,
            course.publishDate, isFavorite, course.img, course.userId
        ))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getFavoriteCourses(): List<CourseMainInfo>{
        return courseDao.getFavoriteCourses(dataStoreRepository.getCurrentUserId()).map {
            it.toCourseUI()
        }
    }
}