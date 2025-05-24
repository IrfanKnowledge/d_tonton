plugins {
    alias(libs.plugins.android.dynamic.feature)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize") // parcelize plugin
    alias(libs.plugins.androidx.navigation.safeargs.kotlin) // navigation component safe args plugin
    alias(libs.plugins.devtools.ksp) // ksp pengganti kapt
}
android {
    namespace = "com.irfan.favorite"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
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
}

dependencies {
    implementation(project(":core"))
    implementation(project(":app"))

    implementation(libs.androidx.navigation.fragment.ktx) // navigation component
    implementation(libs.androidx.navigation.ui.ktx) // navigation component
    // in fragments, example: val someViewModel: by viewModels()
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.swiperefreshlayout) // swipe refresh

    implementation(libs.hilt.android) // dagger hilt
    ksp(libs.hilt.android.compiler) // dagger hilt

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}