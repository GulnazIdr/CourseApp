package com.example.courseapp.presentation.main.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courseapp.app.CourseApplication
import com.example.courseapp.databinding.FragmentHomeBinding
import com.example.courseapp.presentation.main.CourseMainVIewModelFactory
import com.example.courseapp.presentation.main.CourseViewModel
import com.example.domain.R.layout.loading_dialog
import com.example.domain.presentation.CourseCardStateAdapter
import com.example.domain.presentation.models.CourseUi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.domain.R

class HomeFragment : Fragment() {
    @Inject lateinit var vmFactory: CourseMainVIewModelFactory
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

        var adapter = CourseCardStateAdapter( mutableListOf<CourseUi>(), context){
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
                    combine(
                        courseViewModel.isSearching,
                        courseViewModel.filteredCourseList,
                        courseViewModel.courseList
                    ) { isSearching, filtered, all ->
                        if (isSearching) filtered else all
                    }
                        .distinctUntilChanged()
                        .collect { courses ->
                            adapter.updateCourseList(courses)
                        }
                }
            }
        }

        binding.filterChoice.setOnClickListener {courseViewModel.sortByPublishDate()}

        binding.searchField.doOnTextChanged { keyword, _, _, _->
            courseViewModel.searchByKeyword(keyword)
        }

        return binding.root
    }

    fun showLoading(){
        val loadingView: View = LayoutInflater
            .from(requireContext())
            .inflate(loading_dialog,null) as LinearLayout
        loadingDialog = AlertDialog.Builder(requireContext(), R.style.loadingDialog)
            .setView(loadingView).create()
        loadingDialog!!.show()
    }

}