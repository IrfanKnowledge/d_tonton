plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize") // parcelize plugin
    alias(libs.plugins.androidx.navigation.safeargs.kotlin) // navigation component safe args plugin
    alias(libs.plugins.devtools.ksp) // ksp pengganti kapt
    alias(libs.plugins.dagger.hilt.android) // dagger hilt
}

android {
    namespace = "com.irfan.dtonton"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.irfan.dtonton"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            buildConfigField("Boolean", "DEBUG", "true")
            buildConfigField("String", "API_KEY", "\"c5a5c8800007bed6b8c89e13c32b2266\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("Boolean", "DEBUG", "false")
            buildConfigField("String", "API_KEY", "\"c5a5c8800007bed6b8c89e13c32b2266\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    dynamicFeatures += setOf(":favorite")
}

dependencies {
    implementation(project(":core"))

    implementation(libs.feature.delivery) // module dynamic feature
    implementation(libs.androidx.legacy.support.v4)

    implementation(libs.hilt.android) // dagger hilt
    ksp(libs.hilt.android.compiler) // dagger hilt
    implementation(libs.androidx.swiperefreshlayout) // swipe refresh

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}