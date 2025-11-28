package com.example.domain.presentation

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.common_presentation.R
import com.example.domain.presentation.models.CourseUi
import com.example.domain.databinding.CourseCardItemBinding
import java.text.SimpleDateFormat
import java.util.Locale

class CourseCardStateAdapter(
    private var courseList: MutableList<CourseUi>,
    private val context: Context?,
    private val onItem: (Int) -> Unit
): RecyclerView.Adapter<CourseCardStateAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): ViewHolder {
        val view = CourseCardItemBinding.inflate(LayoutInflater.from(p0.context), p0, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(courseList[p1], object : CustomClickListener {
            override fun onFavorite(id: Int) {
                onItem(id)
            }
        })
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    fun updateCourseList(list: List<CourseUi>){
        var initialSize = courseList.size
        courseList.clear()
        courseList.addAll(list)

        notifyDataSetChanged()

        initialSize = courseList.size
    }

    inner class ViewHolder(
        private val binding: CourseCardItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(card: CourseUi, customClickListener: CustomClickListener){
            try {
                binding.cardCover.setImageResource(card.img)
            }catch (e: Exception){
                Log.e("set image resource ${card.img} failed: ", "${e.message} ${e::class.simpleName}")
                binding.cardCover.setImageResource(R.drawable.java_image )
            }

            setFavoriteIcon(card.isFavorite)
            binding.cardTitle.text = card.title
            binding.cardDescr.text = card.descr
            binding.cardPrice.text =
                context?.getString(R.string.price, card.price) ?: card.price.toString()
            binding.rate.text = card.rate.toString()
            binding.courseDate.text = formatDate(card.publishDate.toString())

            binding.favoriteIconGroup.setOnClickListener{

                customClickListener.onFavorite(card.id)
                setFavoriteIcon(!card.isFavorite)
            }
        }

        private fun setFavoriteIcon(isFavorite: Boolean = false){
            Log.d("Dfd", "${isFavorite}")
            var img = R.drawable.favorite_icon
            if(isFavorite)
                img = R.drawable.favorite_filled_icon
            else img = R.drawable.favorite_icon
            binding.favoriteIcon.setImageResource(img)
        }

        private fun formatDate(dateString: String): String {
            return try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.forLanguageTag("ru"))
                val date = inputFormat.parse(dateString)
                outputFormat.format(date ?: return dateString)
            } catch (e: Exception) {
                dateString
            }
        }

    }
}