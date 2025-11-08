package com.example.courseapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.courseapp.databinding.CourseCardItemBinding
import com.example.courseapp.presentation.classes.CourseMainInfo

class CourseCardStateAdapter(
    private val courseList: List<CourseMainInfo>
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

//    override fun getItemViewType(position: Int): Int {
//        return courseList[position].id
//    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    inner class ViewHolder(
        private val binding: CourseCardItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(card: CourseMainInfo){
            binding.cardCover.setImageURI(card.img.toUri())
            binding.cardTitle.text = card.title
            binding.cardDescr.text = card.descr
            binding.cardPrice.text = card.price.toString()
            binding.rate.text = card.rate.toString()
            binding.courseDate.text = card.date.toString()
        }
    }
}