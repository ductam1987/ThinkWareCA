
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by Tam Nguyen on 11/07/2024.
 */
class AndroidObjectBoxLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("io.objectbox")
        }
    }
}
