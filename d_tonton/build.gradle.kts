// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.dynamic.feature) apply false // dynamic feature
    alias(libs.plugins.devtools.ksp) apply false // ksp pengganti kapt
    alias(libs.plugins.dagger.hilt.android) apply false // dagger hilt
}

buildscript {
    dependencies {
        classpath(libs.androidx.navigation.safe.args.gradle.plugin) // navigation component safe args
    }
}