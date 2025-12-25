// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false

    id("com.google.devtools.ksp") version "2.3.3"

    alias(libs.plugins.android.library) apply false

    val room_version = "2.8.3"
    id("androidx.room") version "$room_version" apply false

    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20"
}