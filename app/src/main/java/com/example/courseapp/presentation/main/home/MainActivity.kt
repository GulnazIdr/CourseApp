package com.example.courseapp.presentation.main.home

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.courseapp.R
import com.example.courseapp.databinding.ActivityMainBinding
import com.example.courseapp.presentation.main.favorite.FavoriteFragment

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)

        if(savedInstanceState == null) {
            setCurrentFragment(HomeFragment())
        }

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    setCurrentFragment(HomeFragment())
                    true
                }
                R.id.favorite -> {
                    setCurrentFragment(FavoriteFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContent, fragment)
            commit()
        }
    }

}