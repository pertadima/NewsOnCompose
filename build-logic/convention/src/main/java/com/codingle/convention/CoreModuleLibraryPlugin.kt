import com.android.build.gradle.LibraryExtension
import com.codingle.convention.Constant.COMPILE_SDK
import com.codingle.convention.Constant.MIN_SDK
import com.codingle.convention.Extensions.libs
import org.gradle.api.JavaVersion.VERSION_1_8
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class CoreModuleLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            pluginManager.apply("org.jetbrains.kotlin.android")
            pluginManager.apply("com.google.devtools.ksp")
            pluginManager.apply("dagger.hilt.android.plugin")
            pluginManager.apply("io.gitlab.arturbosch.detekt")

            extensions.configure<LibraryExtension> {
                compileSdk = COMPILE_SDK

                defaultConfig {
                    minSdk = MIN_SDK
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
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

                // Dynamically set namespace based on module name
                namespace = "com.codingle.${project.name.replace("-", "_")}"
            }

            dependencies {
                libs("androidx.core.ktx")?.let { add("implementation", it) }
                libs("junit")?.let { add("testImplementation", it) }
                libs("androidx.junit")?.let { add("androidTestImplementation", it) }
                libs("androidx.espresso.core")?.let { add("androidTestImplementation", it) }

                libs("daggerHilt")?.let { add("implementation", it) }
                libs("daggerHiltCompiler")?.let { add("ksp", it) }
                libs("gson")?.let { add("implementation", it) }
                libs("room")?.let { add("implementation", it) }
                libs("room.ktx")?.let { add("implementation", it) }
                libs("roomCompiler")?.let { add("ksp", it) }

                // Add local module dependency dynamically
                if (project.name != "core-data") {
                    add("implementation", project(":core-data"))
                }
            }
        }
    }
}
