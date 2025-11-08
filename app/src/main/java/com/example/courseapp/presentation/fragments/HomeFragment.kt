package com.example.courseapp.presentation.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courseapp.R
import com.example.courseapp.databinding.FragmentHomeBinding
import com.example.courseapp.presentation.adapters.CourseCardStateAdapter
import com.example.courseapp.presentation.classes.CourseMainInfo
import java.time.LocalDate

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val recView = binding.recycler.courseCardRecycler

        val recyclerView: MutableList<CourseMainInfo> = ArrayList()
        recyclerView.add(
            CourseMainInfo(0, "hello", "descirption", 1.7f, 3.7f, LocalDate.now(), false, "")
        )
        recyclerView.add(
            CourseMainInfo(0, "hello", "descirption", 1.7f, 3.7f, LocalDate.now(), false, "")
        )
        recyclerView.add(
            CourseMainInfo(0, "hello", "descirption", 1.7f, 3.7f, LocalDate.now(), false, "")
        )
        recyclerView.add(
            CourseMainInfo(0, "hello", "descirption", 1.7f, 3.7f, LocalDate.now(), false, "")
        )
        recyclerView.add(
            CourseMainInfo(0, "hello", "descirption", 1.7f, 3.7f, LocalDate.now(), false, "")
        )
        recyclerView.add(
            CourseMainInfo(0, "hello", "descirption", 1.7f, 3.7f, LocalDate.now(), false, "")
        )
        recyclerView.add(
            CourseMainInfo(0, "hello", "descirption", 1.7f, 3.7f, LocalDate.now(), false, "")
        )
        recyclerView.add(
            CourseMainInfo(0, "hello", "descirption", 1.7f, 3.7f, LocalDate.now(), false, "")
        )
        recyclerView.add(
            CourseMainInfo(0, "hello", "descirption", 1.7f, 3.7f, LocalDate.now(), false, "")
        )
        recyclerView.add(
            CourseMainInfo(0, "hello", "descirption", 1.7f, 3.7f, LocalDate.now(), false, "")
        )
        recyclerView.add(
            CourseMainInfo(0, "hello", "descirption", 1.7f, 3.7f, LocalDate.now(), false, "")
        )

        recView.layoutManager = LinearLayoutManager(context)
        val adapter = CourseCardStateAdapter(recyclerView)
        recView.adapter = adapter

        return binding.root
    }

}