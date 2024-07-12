plugins {
    `kotlin-dsl`
}

group = "com.tca.build.buildlogic" // your module name

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.objectbox.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "tca.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        register("androidApplication") {
            id = "tca.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = "tca.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }

        register("androidLibrary") {
            id = "tca.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidFeature") {
            id = "tca.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }

        register("ObjectBox") {
            id = "tca.objectbox"
            implementationClass = "AndroidObjectBoxLibraryPlugin"
        }
    }
}
