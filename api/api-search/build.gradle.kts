import org.gradle.api.JavaVersion.VERSION_1_8

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp.library)
    alias(libs.plugins.dagger.library)
    alias(libs.plugins.detekt.library)
}

android {
    namespace = "com.codingle.api_search"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = VERSION_1_8
        targetCompatibility = VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.daggerHilt)
    ksp(libs.daggerHiltCompiler)
    implementation(libs.gson)
    implementation(libs.retrofit)
    implementation(libs.room)
    implementation(libs.room.ktx)
    ksp(libs.roomCompiler)

    // local module
    implementation(project(":core-data"))
}