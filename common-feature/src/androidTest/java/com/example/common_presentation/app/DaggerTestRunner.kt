package com.example.common_presentation.app

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class DaggerTestRunner: AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application? {
        return super.newApplication(cl, TestApplication::class.java.name, context)
    }
}