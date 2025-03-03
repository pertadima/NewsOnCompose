plugins {
    alias(libs.plugins.dependencies.build.logic)
    alias(libs.plugins.core.build.logic)
    alias(libs.plugins.compose.library)
}

android {
    buildFeatures { compose = true }
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.lottie)
    implementation(libs.coil.compose)
}