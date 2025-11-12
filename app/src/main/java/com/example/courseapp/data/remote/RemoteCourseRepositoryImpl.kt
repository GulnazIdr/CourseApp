package com.example.courseapp.data.remote

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.courseapp.data.local.dao.CourseDao
import com.example.courseapp.data.mappers.CourseMapper
import com.example.courseapp.data.remote.retrofit.CourseAPI
import com.example.courseapp.domain.CourseRepository
import com.example.courseapp.domain.DataStoreRepository
import com.example.courseapp.domain.FetchedResult
import com.example.courseapp.presentation.main.CourseMainInfo
import javax.inject.Inject

class RemoteCourseRepositoryImpl @Inject constructor(
    private val courseAPI: CourseAPI,
    private val courseDao: CourseDao,
    private val dataStoreRepository: DataStoreRepository
): CourseRepository, CourseMapper() {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun fetchCourses(): FetchedResult<List<CourseMainInfo>> {
        try {
            val fetched = courseAPI.fetchCourses()
            val favorites = courseDao
                .getFavoriteCourses(dataStoreRepository.getCurrentUserId())
                .associateBy { it.id }

            return FetchedResult.Success(
                if (favorites.isEmpty()) {
                    fetched.courses.map { it.toCourseUi(it.hasLike == true) }
                }else {
                    fetched.courses.map {
                        it.toCourseUi(
                            favorites[it.id]!!.isFavorite
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