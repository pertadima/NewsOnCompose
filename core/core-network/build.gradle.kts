import org.gradle.api.JavaVersion.VERSION_1_8

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp.library)
    alias(libs.plugins.detekt.library)
}

android {
    namespace = "com.codingle.network"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val apiConfigFile = rootProject.file("./buildProperties/envi.properties")
        (apiConfigFile.exists()).let {
            apiConfigFile.forEachLine { line ->
                val entry = line.split("=", limit = 2)
                rootProject.extra.set(entry.first(), entry.last())
            }
        }

        val baseUrl = if (rootProject.extra.has("BASE_URL")) rootProject.extra["BASE_URL"] else "\"\""
        val apiKey = if (rootProject.extra.has("API_KEY")) rootProject.extra["API_KEY"] else "\"\""
        buildConfigField("String", "BASE_URL", baseUrl.toString())
        buildConfigField("String", "API_KEY", apiKey.toString())
    }

    buildFeatures { buildConfig = true }

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

    // network
    implementation(libs.okhttp)
    implementation(libs.okhttpLogging)
    implementation(libs.retrofit)
    implementation(libs.retrofitGson)
}