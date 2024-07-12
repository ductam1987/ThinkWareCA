plugins {
    id("tca.android.library")
    id("tca.android.library.compose")
    id("kotlinx-serialization")
    id("tca.objectbox")
}

android {
    namespace = "com.tca.core.model"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    api(libs.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
