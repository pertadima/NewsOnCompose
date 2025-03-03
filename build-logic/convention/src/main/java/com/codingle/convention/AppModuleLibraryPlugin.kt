
import com.android.build.api.dsl.ApplicationExtension
import com.codingle.convention.Constant.COMPILE_SDK
import com.codingle.convention.Constant.MIN_SDK
import com.codingle.convention.Constant.TARGET_SDK
import com.codingle.convention.Constant.VERSION_CODE
import com.codingle.convention.Constant.VERSION_NAME
import org.gradle.api.JavaVersion.VERSION_1_8
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension


class AppModuleLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")
            pluginManager.apply("org.jetbrains.kotlin.android")
            pluginManager.apply("com.google.devtools.ksp")
            pluginManager.apply("dagger.hilt.android.plugin")
            pluginManager.apply("io.gitlab.arturbosch.detekt")
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            extensions.configure<ApplicationExtension> {
                compileSdk = COMPILE_SDK

                defaultConfig {
                    applicationId = "com.codingle.newsoncompose"
                    minSdk = MIN_SDK
                    targetSdk = TARGET_SDK
                    versionCode = VERSION_CODE
                    versionName = VERSION_NAME
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    vectorDrawables.useSupportLibrary = true
                }

                buildTypes {
                    getByName("release") {
                        isMinifyEnabled = false
                        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                    }
                }

                compileOptions {
                    sourceCompatibility = VERSION_1_8
                    targetCompatibility = VERSION_1_8
                }

                extensions.configure<KotlinAndroidProjectExtension> {
                    compilerOptions {
                        jvmTarget.set(JVM_1_8)
                    }
                }

                buildFeatures.compose = true

                packaging.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"

                namespace = "com.codingle.newsoncompose"
            }
        }
    }
}