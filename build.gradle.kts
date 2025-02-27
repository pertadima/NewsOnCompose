// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.dagger.library) apply false
    alias(libs.plugins.ksp.library) apply false
    alias(libs.plugins.detekt.library) apply false
    alias(libs.plugins.compose.library) apply false
}