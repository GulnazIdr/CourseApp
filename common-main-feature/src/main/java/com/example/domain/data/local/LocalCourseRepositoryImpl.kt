package com.example.domain.data.local

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.common_feature.data.dao.CourseDao
import com.example.common_feature.domain.CourseRepository
import com.example.common_feature.domain.DataStoreRepository
import com.example.common_feature.domain.FetchedResult
import com.example.common_feature.domain.models.Course
import com.example.domain.data.mappers.CourseMapper
import com.example.domain.domain.LocalCourseRepository
import javax.inject.Inject

class LocalCourseRepositoryImpl @Inject constructor(
    private val courseDao: CourseDao,
    private val dataStoreRepository: DataStoreRepository
): LocalCourseRepository, CourseRepository, CourseMapper(){

    @RequiresApi(Build.VERSION_CODES.O)
     override suspend fun fetchCourses(): FetchedResult<List<Course>> {
         try {
             val fetched = courseDao
                 .fetchCourses(dataStoreRepository.getCurrentUserId())
                 .map { it.toCourse() }
             return FetchedResult.Success(fetched)
         }catch (e: Exception){
             Log.e("LOCAL FETCH COURSE ", "${e::class.simpleName} ${e.message}");
             return FetchedResult.Error(e.message.toString())
         }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun saveCourses(course: Course){
        try {
            courseDao.saveCourse(course.toCourseEntity())
        }catch (e: Exception){
            Log.e("LOCAL SAVE COURSE ", "${e::class.simpleName} ${e.message}");
        }
    }

    override suspend fun isInLocalDb(id: Int): Boolean{
        try {
            return courseDao.getCourseById(id, dataStoreRepository.getCurrentUserId()) != null
        }catch (e: Exception){
            Log.e("LOCAL CHECK IN LOCAL COURSE ", "${e::class.simpleName} ${e.message}");
            return false;
        }
    }

    override suspend fun setFavoriteById(id: Int){
        try {
            courseDao.updateFavoriteStatus(id, dataStoreRepository.getCurrentUserId())
        }catch (e: Exception){
            Log.e("LOCAL UPDATE COURSE ", "${e::class.simpleName} ${e.message}");
        }
    }
}