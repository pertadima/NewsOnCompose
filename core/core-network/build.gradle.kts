plugins {
    alias(libs.plugins.dependencies.build.logic)
    alias(libs.plugins.core.build.logic)
}


android {
    defaultConfig {
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
}

dependencies {
    implementation(libs.okhttp)
    implementation(libs.okhttpLogging)
    implementation(libs.retrofit)
    implementation(libs.retrofitGson)
}