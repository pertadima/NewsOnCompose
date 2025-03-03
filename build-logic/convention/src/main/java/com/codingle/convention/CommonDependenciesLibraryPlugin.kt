import com.codingle.convention.Extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


class CommonDependenciesLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            pluginManager.apply("org.jetbrains.kotlin.android")
            pluginManager.apply("com.google.devtools.ksp")
            pluginManager.apply("dagger.hilt.android.plugin")
            pluginManager.apply("io.gitlab.arturbosch.detekt")

            dependencies {
                libs("androidx.core.ktx")?.let { add("implementation", it) }
                libs("junit")?.let { add("testImplementation", it) }
                libs("androidx.junit")?.let { add("androidTestImplementation", it) }
                libs("androidx.espresso.core")?.let { add("androidTestImplementation", it) }

                libs("daggerHilt")?.let { add("implementation", it) }
                libs("daggerHiltCompiler")?.let { add("ksp", it) }
                libs("gson")?.let { add("implementation", it) }
                libs("retrofit")?.let { add("implementation", it) }
                libs("room")?.let { add("implementation", it) }
                libs("room.ktx")?.let { add("implementation", it) }
                libs("roomCompiler")?.let { add("ksp", it) }
            }
        }
    }
}