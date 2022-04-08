package sdksetup

import appendStringByMatch
import java.io.File

class SDKSetupSecondStepController {

    fun addSDKToBuildGradle(basePath: String) {
        val projectBuildGradle = File("$basePath/build.gradle")
        var content: String = projectBuildGradle.readText()
//        content = content.appendStringByMatch("mavenCentral()", "gradlePluginPortal()", "\n\t\t")
//        content = content.appendStringByMatch("jcenter()", "gradlePluginPortal()", "\n\t\t")
        projectBuildGradle.writeText(content)
    }
}