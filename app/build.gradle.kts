@file:Suppress("UnstableApiUsage")
import com.android.build.OutputFile
plugins {
    id("tca.android.application")
    id("tca.android.application.compose")
}

android {
    namespace = "com.thinkwarecleanarchitecture"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.thinkwarecleanarchitecture"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    splits {
        abi {
            isEnable = true
            reset()
            include("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
            isUniversalApk = false
        }
    }
}

val abiCodes = mapOf(
    "x86" to 1,
    "x86_64" to 2,
    "armeabi-v7a" to 3,
    "arm64-v8a" to 4
)

dependencies {

    implementation(project(":core:network"))
    implementation(project(":core:repos"))
    implementation(project(":core:model"))
    implementation(project(":feature:home"))
    implementation(project(":feature:detail"))
    implementation(project(":feature:videoffmpeg"))

    api(libs.androidx.test.runner)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.koin.worker)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompact)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.accompanist)
    implementation(libs.androidx.animation)
    testImplementation(libs.junit4)
    androidTestImplementation(kotlin("test"))
    androidTestImplementation(libs.androidx.compose.ui.test)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.test.runner)
    debugImplementation(libs.androidx.compose.ui.test)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation(libs.orhanobut.logger)
    implementation(libs.kotlinx.serialization.json)
}
