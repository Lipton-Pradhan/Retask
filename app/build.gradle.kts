plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // KSP
    id("com.google.devtools.ksp")

    // Kapt
//    id("org.jetbrains.kotlin.kapt")

    // Hilt-Dependency Injection
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.retask"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.retask"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "com.example.retask.CustomTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // KSP
    ksp(libs.androidx.room.compiler)

    // Kapt
//    kapt(libs.androidx.room.compiler.v250)

    // Room Database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    // Hilt-Dependency Injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Hilt Robolectric
    testImplementation(libs.hilt.android.testing)
    // ...Kotlin.
//    kaptTest(libs.hilt.android.compiler)

    // Hilt Instrumented Tests
    androidTestImplementation(libs.hilt.android.testing)
    // ...with Kotlin.
//    kaptAndroidTest(libs.hilt.android.compiler)
}