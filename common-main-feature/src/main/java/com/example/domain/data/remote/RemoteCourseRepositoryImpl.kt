package com.example.domain.data.remote

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.common_feature.data.dao.CourseDao
import com.example.common_feature.domain.CourseRepository
import com.example.common_feature.domain.FetchedResult
import com.example.common_feature.domain.UserRepository
import com.example.common_feature.domain.models.Course
import com.example.domain.data.mappers.CourseMapper
import com.example.domain.data.remote.retrofit.CourseAPI
import javax.inject.Inject

class RemoteCourseRepositoryImpl @Inject constructor(
    private val courseAPI: CourseAPI,
    private val courseDao: CourseDao,
    private val userRepository: UserRepository
): CourseRepository, CourseMapper() {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun fetchCourses(): FetchedResult<List<Course>> {
        try {
            val currentId: String? = userRepository.getCurrentUser()?.email
            val fetched = courseAPI.fetchCourses()
            val favorites =
                if(currentId != null)
                    courseDao.getFavoriteCourses(currentId)?.associateBy { it.id }
                else mapOf()

            return FetchedResult.Success(
                if (favorites != null && favorites.isEmpty()) {
                    fetched.courses.map { it.toCourse(it.hasLike == true) }
                }else {
                    fetched.courses.map {
                        it.toCourse(
                            favorites?.get(it.id)!!.isFavorite
                        )
                    }
                }
            )
        }catch (e: Exception){
            Log.e("FETCHING ERROR", "${e.message} ${e::class.simpleName}")
            return FetchedResult.Error(e.message.toString())
        }
    }
}