import org.gradle.api.initialization.resolve.RepositoriesMode.FAIL_ON_PROJECT_REPOS

pluginManagement {
    includeBuild("build-logic")
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
        maven(url = "https://jitpack.io")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "ThinkwareCleanArchitecture"
include(":app")
include(":core:repos")
include(":core:model")
include(":core:testing")
// include(":core:KoinDI")
include(":core:ui")
include(":core:network")
include(":feature:home")
include(":feature:detail")
include(":feature:videoffmpeg")
