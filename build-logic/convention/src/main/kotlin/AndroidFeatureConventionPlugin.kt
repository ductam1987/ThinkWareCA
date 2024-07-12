import com.tca.build.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

/**
 * Created by Tam Nguyen on 11/07/2024.
 */

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("tca.android.library")
            }
//            extensions.configure<LibraryExtension> {
//                defaultConfig {
//                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//                }
//            }

            dependencies {
                add("implementation", project(":core:model"))
                add("implementation", project(":core:network"))
                add("implementation", project(":core:repos"))
                add("implementation", project(":core:ui"))

                add("testImplementation", kotlin("test"))
                add("testImplementation", project(":core:testing"))
                add("androidTestImplementation", kotlin("test"))
                add("androidTestImplementation", project(":core:testing"))

                add("implementation", libs.findLibrary("coil.kt").get())
                add("implementation", libs.findLibrary("coil.kt.compose").get())

                add("implementation", libs.findLibrary("lifecycle.runtime.compose").get())
                add("implementation", libs.findLibrary("viewmodel.compose").get())

                add("implementation", libs.findLibrary("koin.compose").get())
                add("implementation", libs.findLibrary("koin.android").get())
                add("implementation", libs.findLibrary("koin.worker").get())

                add("implementation", libs.findLibrary("sandwich.network").get())

                add("implementation", libs.findLibrary("androidx.compose.ui.tooling.preview").get())
                add("implementation", libs.findLibrary("androidx-compose-ui-tooling").get())
                add("implementation", libs.findLibrary("androidx-customview").get())
                add("implementation", libs.findLibrary("androidx-customview-poolingcontainer").get())

                add("implementation", libs.findLibrary("androidx-compose-constraintlayout").get())
                add("implementation", libs.findLibrary("ffmpeg-kit-full").get())

                add("androidTestImplementation", libs.findLibrary("androidx-test-runner").get())
                add("androidTestImplementation", libs.findLibrary("androidx-test-rules").get())
            }
        }
    }
}
