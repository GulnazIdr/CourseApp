package com.example.courseapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.courseapp.data.local.entity.CourseEntity

@Dao
interface CourseDao{
    @Query("SELECT * FROM courseentity")
    suspend fun fetchCourses(): List<CourseEntity>

    @Upsert
    fun saveCourse(courseEntity: CourseEntity)

    @Query("SELECT * FROM COURSEENTITY WHERE id=:id and userId=:userId")
    suspend fun getCourseById(id: Int, userId: String): CourseEntity?

    @Update
    fun setFavoriteById(courseEntity: CourseEntity)

    @Query("SELECT * FROM COURSEENTITY WHERE userId=:userId")
    suspend fun getFavoriteCourses(userId: String): List<CourseEntity>
}