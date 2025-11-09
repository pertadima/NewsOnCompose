plugins {
    alias(libs.plugins.application.build.logic)
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.hilt.common)
    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)

    implementation(libs.coil.compose)
    implementation(libs.daggerHilt)
    implementation(libs.hilt.navigation)
    ksp(libs.daggerHiltCompiler)
    implementation(libs.lottie)

    // network
    implementation(libs.okhttp)
    implementation(libs.okhttpLogging)
    implementation(libs.retrofit)
    implementation(libs.retrofitGson)
    implementation(libs.room)
    implementation(libs.room.ktx)
    ksp(libs.roomCompiler)

    // local module
    implementation(project(":core"))
    implementation(project(":core-data"))
    implementation(project(":core-network"))
    implementation(project(":core-ui"))

    implementation(project(":api-sources"))
    implementation(project(":api-headlines"))
    implementation(project(":api-search"))
    implementation(project(":api-settings"))
}