package com.example.courseapp.presentation.main

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.courseapp.R
import com.example.courseapp.databinding.CourseCardItemBinding
import com.example.courseapp.presentation.main.CourseMainInfo

class CourseCardStateAdapter(
    private val courseList: List<CourseMainInfo>,
    private val context: Context?
): RecyclerView.Adapter<CourseCardStateAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): ViewHolder {
        val view = CourseCardItemBinding.inflate(LayoutInflater.from(p0.context), p0, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        return p0.bind(courseList[p1])
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    inner class ViewHolder(
        private val binding: CourseCardItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(card: CourseMainInfo){
            try {
                binding.cardCover.setImageResource(card.img)
            }catch (e: Exception){
                Log.e("set image resource ${card.img} failed: ", "${e.message} ${e::class.simpleName}")
                binding.cardCover.setImageResource(R.drawable.java_image)
            }

            binding.cardTitle.text = card.title
            binding.cardDescr.text = card.descr
            binding.cardPrice.text =
                context?.getString(R.string.price, card.price) ?: card.price.toString()
            binding.rate.text = card.rate.toString()
            binding.courseDate.text = card.startDate.toString()
        }
    }
}