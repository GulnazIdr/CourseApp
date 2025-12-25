package com.example.domain.data.local

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.common_feature.data.dao.CourseDao
import com.example.common_feature.domain.CourseRepository
import com.example.common_feature.domain.FetchedResult
import com.example.common_feature.domain.UserRepository
import com.example.common_feature.domain.models.Course
import com.example.domain.data.mappers.CourseMapper
import com.example.domain.domain.LocalCourseRepository
import javax.inject.Inject

class LocalCourseRepositoryImpl @Inject constructor(
    private val courseDao: CourseDao,
    private val userRepository: UserRepository
): LocalCourseRepository, CourseRepository, CourseMapper(){
    
    private suspend fun currentUser(): String? = userRepository.getCurrentUser()?.email

    @RequiresApi(Build.VERSION_CODES.O)
     override suspend fun fetchCourses(): FetchedResult<List<Course>> {
         try {
             return if(currentUser() != null) {
                 val fetched = courseDao
                     .fetchCourses(currentUser()!!)
                     .map { it.toCourse() }
                 FetchedResult.Success(fetched)
             }else
                 FetchedResult.Error("user id is empty")
         }catch (e: Exception){
             Log.e("LOCAL FETCH COURSE ", "${e::class.simpleName} ${e.message}");
             return FetchedResult.Error(e.message.toString())
         }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun saveCourses(course: Course){
        try {
            if(currentUser() != null)
                courseDao.saveCourse(course.toCourseEntity(currentUser()!!))
        }catch (e: Exception){
            Log.e("LOCAL SAVE COURSE ", "${e::class.simpleName} ${e.message}");
        }
    }

    override suspend fun isInLocalDb(id: Int): Boolean{
        try {
            return if(currentUser() != null)
                courseDao.getCourseById(id, currentUser()!!) != null
            else
                false
        }catch (e: Exception){
            Log.e("LOCAL CHECK IN LOCAL COURSE ", "${e::class.simpleName} ${e.message}");
            return false;
        }
    }

    override suspend fun setFavoriteById(id: Int){
        try {
            if(currentUser() != null)
                courseDao.updateFavoriteStatus(id,currentUser()!!)
        }catch (e: Exception){
            Log.e("LOCAL UPDATE COURSE ", "${e::class.simpleName} ${e.message}");
        }
    }
}