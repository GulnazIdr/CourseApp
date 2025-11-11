package com.example.courseapp.presentation.main.favorite

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courseapp.R
import com.example.courseapp.app.CourseApplication
import com.example.courseapp.databinding.FragmentFavoriteBinding
import com.example.courseapp.presentation.main.CourseCardStateAdapter
import com.example.courseapp.presentation.main.CourseMainInfo
import com.example.courseapp.presentation.main.CourseMainVIewModelFactory
import com.example.courseapp.presentation.main.CourseViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class FavoriteFragment : Fragment() {
    @Inject
    lateinit var vmFactory: CourseMainVIewModelFactory
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var courseViewModel: CourseViewModel
    private var loadingDialog: AlertDialog? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        (activity?.applicationContext as CourseApplication).appComponent.inject(this)
        courseViewModel = ViewModelProvider(this, vmFactory)[CourseViewModel::class.java]

        val recycler = binding.recycler.courseCardRecycler
        recycler.layoutManager = LinearLayoutManager(context)
        var adapter = CourseCardStateAdapter(mutableListOf(), context)

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
                        adapter = CourseCardStateAdapter(it.filter { it.isFavorite }, context)
                        recycler.adapter = adapter
                    }
                }
            }
        }

        return binding.root
    }

    fun showLoading(){
        if (loadingDialog == null) {
            loadingDialog = AlertDialog.Builder(requireContext())
                .setMessage("Loading...")
                .setCancelable(false)
                .create()
        }
        loadingDialog?.show()
    }
}