package com.example.favorite_feature.presentation.fragments

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
import com.example.domain.R
import com.example.domain.presentation.CourseCardStateAdapter
import com.example.domain.presentation.models.CourseUi
import com.example.favorite_feature.databinding.FragmentFavoriteBinding
import com.example.favorite_feature.di.CommonFeatureComponent
import com.example.favorite_feature.presentation.FavoriteVIewModelFactory
import com.example.favorite_feature.presentation.FavoriteViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteFragment : Fragment() {
    @Inject
    lateinit var vmFactory: FavoriteVIewModelFactory
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var courseViewModel: FavoriteViewModel
    private var loadingDialog: AlertDialog? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        (activity?.applicationContext as CommonFeatureComponent).injectCommonFeature(this)
        courseViewModel = ViewModelProvider(this, vmFactory)[FavoriteViewModel::class.java]

        val recycler = binding.recycler.courseCardRecycler
        recycler.layoutManager = LinearLayoutManager(context)
        val adapter = CourseCardStateAdapter(mutableListOf<CourseUi>(), context) {
            courseViewModel.onFavorite(it)
        }
        recycler.adapter = adapter

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
                        adapter.updateCourseList(it.filter { it.isFavorite })
                    }
                }
            }
        }

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