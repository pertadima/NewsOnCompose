plugins {
    alias(libs.plugins.dependencies.build.logic)
    alias(libs.plugins.core.build.logic)
}

dependencies {
    implementation(libs.coil.compose)
}