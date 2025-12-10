package com.example.courseapp

import com.example.common_feature.domain.models.Course
import com.example.courseapp.presentation.main.CourseViewModel
import org.junit.Test
import org.junit.Assert.assertEquals

class CourseUnitTest {

    val courseList = listOf<Course>(
        Course(publishDate = "14.12.1990"),
        Course(publishDate = "04.10.2020"),
        Course(publishDate = "15-10-2022"),
        Course(publishDate = "25/11/2025"),
        Course(publishDate = "14.12.2025"),
        Course( publishDate = "5.1.2030"),
        Course(publishDate = "5.1.203"),
        Course(publishDate = "date"),
    )
    val emptyCourseList = emptyList<Course>()

    @Test
    fun `sort courses in descending order by publish date`(){
        courseList.sortedByDescending { it.publishDate }
        assertEquals("14.12.1990", courseList[0].publishDate)
        assertEquals("04.10.2020", courseList[1].publishDate)
        assertEquals("15-10-2022", courseList[2].publishDate)
        assertEquals("25/11/2025", courseList[3].publishDate)
        assertEquals("14.12.2025", courseList[4].publishDate)

        emptyCourseList.sortedByDescending { it.publishDate }
    }
}