@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("tca.android.library")
    id("tca.android.library.compose")
}

android {
    namespace = "com.tca.core.testing"
}

dependencies {

    api(libs.androidx.compose.ui.test)
    api(libs.androidx.test.core)
    api(libs.androidx.test.espresso.core)
    api(libs.androidx.test.rules)
    api(libs.androidx.test.runner)
    api(libs.junit4)
    api(libs.kotlinx.coroutines.test)
    api(libs.turbine)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.koin.worker)
    debugApi(libs.androidx.compose.ui.test.manifest)

    implementation(project(":core:model"))
    // implementation(project(":core:koinDI"))
}
