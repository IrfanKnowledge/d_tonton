plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize") // parcelize plugin
    alias(libs.plugins.devtools.ksp) // ksp pengganti kapt
}

android {
    namespace = "com.irfan.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
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
}

dependencies {
    api(libs.material)
    // untuk image url
    api(libs.glide)

    implementation(libs.retrofit) // request implementation
    implementation(libs.converter.gson) // parsing response implementation
    implementation(libs.logging.interceptor) // logging implementation

    // Library dasar coroutine
    implementation(libs.kotlinx.coroutines.core)
    // Library untuk memudahkan threading di Android
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.hilt.android) // dagger hilt
    ksp(libs.hilt.android.compiler) // dagger hilt

    implementation(libs.androidx.room.runtime) // room database
    ksp(libs.androidx.room.compiler) // room database

    // DB Encrypt
    implementation(libs.android.database.sqlcipher)
    // DB Encrypt
    implementation(libs.androidx.sqlite.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}