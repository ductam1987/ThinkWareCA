plugins {
    id("tca.android.feature")
    id("tca.android.library.compose")
}

android {
    namespace = "com.tca.feature.detail"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompact)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.gowtham.compose.ratingbar)
//    androidTestImplementation(libs.androidx.test.ext)
//    androidTestImplementation(libs.androidx.test.espresso.core)
}
