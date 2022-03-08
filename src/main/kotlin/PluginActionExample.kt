import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import java.io.File

class PluginActionExample : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
//        val devices = event.project?.let {
//            AndroidSdkUtils.getDebugBridge(it)?.devices
//        }

        event.dataContext
        event.project?.let {
            it.showNotification("Path: ${it.basePath}")

            val basePath = it.basePath

            val projectBuildGradle = File("$basePath/build.gradle")

            var content: String = projectBuildGradle.readText()


            val pluginRegex = "gradlePluginPortal()".toRegex()

            it.showNotification("Path: ${pluginRegex.find(content)}")

            content = content.replace("mavenCentral()", "mavenCentral()\n\t\tgradlePluginPortal()")


//            projectBuildGradle.writeText(content)


//            it.showNotification("content: $content")
        }
//        val filename = ""
//        var fileObject = File(filename)
//        var fileExists = fileObject.exists()
//        if(fileExists){
//            print("$filename file does exist.")
//        } else {
//            print("$filename file does not exist.")
//        }

//        var result = ""
//        devices?.forEach {
//            result += "$it "
//        }
//        if (result.isEmpty())
//            event.project?.showNotification("No devices founded")
//        else
//            event.project?.showNotification("$result")
    }

    private fun Project.showNotification(message: String) {
        NotificationGroup("someID", NotificationDisplayType.BALLOON)
            .createNotification(
                "Layout bounds plugin says:",
                message,
                NotificationType.WARNING,
                null
            ).notify(this)
    }
}