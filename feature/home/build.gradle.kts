plugins {
    id("tca.android.feature")
    id("tca.android.library.compose")
}

android {
    namespace = "com.tca.feature.home"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompact)
    implementation(libs.orhanobut.logger)
    api(libs.androidx.compose.ui.test)
    api(libs.androidx.test.core)
    api(libs.androidx.test.espresso.core)
    api(libs.androidx.test.rules)
    api(libs.androidx.test.runner)
    api(libs.junit4)
    api(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)
}
