plugins {
    alias(libs.plugins.core.build.logic)
    alias(libs.plugins.serialization.library)
}

dependencies {
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.retrofit)
    implementation(libs.serialization)
}