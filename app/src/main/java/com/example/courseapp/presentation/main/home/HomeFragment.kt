package com.example.courseapp.presentation.main.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courseapp.R
import com.example.courseapp.app.CourseApplication
import com.example.courseapp.databinding.FragmentHomeBinding
import com.example.courseapp.presentation.main.CourseCardStateAdapter
import com.example.courseapp.presentation.main.CourseMainInfo
import com.example.courseapp.presentation.main.CourseMainVIewModelFactory
import com.example.courseapp.presentation.main.CourseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.mutableListOf

class HomeFragment : Fragment() {
    @Inject
    lateinit var vmFactory: CourseMainVIewModelFactory
    private lateinit var binding: FragmentHomeBinding
    private lateinit var courseViewModel: CourseViewModel
    private var loadingDialog: AlertDialog? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        (activity?.applicationContext as CourseApplication).appComponent.inject(this)
        courseViewModel = ViewModelProvider(this, vmFactory)[CourseViewModel::class.java]

        val recyclerView = binding.recycler.courseCardRecycler
        recyclerView.layoutManager = LinearLayoutManager(context)

        var adapter = CourseCardStateAdapter( mutableListOf<CourseMainInfo>(), context){
            courseViewModel.onFavorite(it)
        }
        recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    courseViewModel.isLoading.collect {
                        if(it) showLoading()
                        else loadingDialog?.dismiss()
                    }
                }

                launch {
                    courseViewModel.courseList.collect {
                        adapter.updateCourseList(it)
                    }
                }
            }
        }

        binding.filterChoice.setOnClickListener {courseViewModel.sortByPublishDate()}

        return binding.root
    }

    fun showLoading(){
        val loadingView: View = LayoutInflater
            .from(requireContext())
            .inflate(R.layout.loading_dialog,null) as ConstraintLayout
        loadingDialog = AlertDialog.Builder(requireContext()).setView(loadingView).create()
        loadingDialog?.show()
    }

}