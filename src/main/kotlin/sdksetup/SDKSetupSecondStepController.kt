package sdksetup

import appendStringByMatch
import com.intellij.openapi.project.Project
import showNotification
import java.io.File

class SDKSetupSecondStepController {

    fun addSDKToAppBuildGradle(basePath: String, appDirectory: String, project: Project) {
        addSDKToAppBuildGradle("$basePath/$appDirectory", project)
    }

    fun addSDKToAppBuildGradle(buildGradlePath: String, project: Project) {
        val projectBuildGradle = File("$buildGradlePath/build.gradle")
        var content: String = projectBuildGradle.readText()

        showNotification(project, content)
        content = content.appendStringByMatch(
            "implementation \"androidx.appcompat:appcompat:[^']*\"",
            "implementation \"com.onesignal:OneSignal:[4.0.0, 4.99.99]\"",
            "\n\t\t",
            project
        )
        content = content.appendStringByMatch(
            "implementation 'androidx.appcompat:appcompat:[^']*'",
            "implementation 'com.onesignal:OneSignal:[4.0.0, 4.99.99]'",
            "\n\t\t",
            project
        )
        content = content.appendStringByMatch(
            "implementation \"com.google.android.material:material:[^']*\"",
            "implementation \"com.onesignal:OneSignal:[4.0.0, 4.99.99]\"",
            "\n\t\t",
            project
        )
        content = content.appendStringByMatch(
            "implementation 'com.google.android.material:material:[^']*'",
            "implementation 'com.onesignal:OneSignal:[4.0.0, 4.99.99]'",
            "\n\t\t",
            project
        )


        content = content.appendStringByMatch(
            "apply plugin: 'com.android.application'",
            "apply plugin: 'com.onesignal.androidsdk.onesignal-gradle-plugin'",
            "\n\t\t",
            project
        )
        content = content.appendStringByMatch(
            "id 'com.android.application'",
            "id 'com.onesignal.androidsdk.onesignal-gradle-plugin'",
            "\n\t\t",
            project
        )
        projectBuildGradle.writeText(content)
    }
}