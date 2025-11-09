package com.example.courseapp.presentation.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courseapp.R
import com.example.courseapp.databinding.FragmentFavoriteBinding
import com.example.courseapp.presentation.adapters.CourseCardStateAdapter
import com.example.courseapp.presentation.classes.CourseMainInfo
import java.time.LocalDate

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        
        val recycler = binding.recycler.courseCardRecycler

        val favoriteCourseList: MutableList<CourseMainInfo> = ArrayList()
        favoriteCourseList.add(
            CourseMainInfo(0, "hello", "descirption", 1.7f, 3.7f, LocalDate.now(), LocalDate.now(), true, R.drawable.java_image)
        )

        recycler.layoutManager = LinearLayoutManager(context)
        val adapter = CourseCardStateAdapter(favoriteCourseList.filter { it.isFavorite }, context)
        recycler.adapter = adapter
        
        return binding.root
    }
}