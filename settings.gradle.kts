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
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
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
include(":api-headlines")
project(":api-headlines").projectDir = File("api/api-headlines")
include(":api-search")
project(":api-search").projectDir = File("api/api-search")
