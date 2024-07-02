import org.gradle.api.initialization.resolve.RepositoriesMode.FAIL_ON_PROJECT_REPOS

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "NewsOnCompose"
include(":app")
include(":core")
project(":core").projectDir = File("core/core")
include(":core-network")
project(":core-network").projectDir = File("core/core-network")
include(":core-data")
project(":core-data").projectDir = File("core/core-data")
include(":core-ui")
project(":core-ui").projectDir = File("core/core-ui")

include(":api-sources")
project(":api-sources").projectDir = File("api/api-sources")
