plugins {
    alias(libs.plugins.api.build.logic)
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.hilt.common)
    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)

    implementation(libs.workManager)
}