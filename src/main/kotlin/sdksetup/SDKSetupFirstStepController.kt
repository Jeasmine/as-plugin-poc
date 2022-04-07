package sdksetup

import appendStringByMatch
import com.intellij.openapi.project.Project
import java.io.File

class SDKSetupFirstStepController {

    fun addSDKToBuildGradle(project: Project) {
        project.let {
            val basePath = it.basePath
            val projectBuildGradle = File("$basePath/build.gradle")
            var content: String = projectBuildGradle.readText()
            content = content.appendStringByMatch("mavenCentral()", "gradlePluginPortal()", "\n\t\t")
            content = content.appendStringByMatch("jcenter()", "gradlePluginPortal()", "\n\t\t")
            projectBuildGradle.writeText(content)
        }
    }
}