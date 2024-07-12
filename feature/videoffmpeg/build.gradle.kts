plugins {
    id("tca.android.feature")
    id("tca.android.library.compose")
}

android {
    namespace = "com.tca.feature.videoffmpeg"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompact)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)
}
