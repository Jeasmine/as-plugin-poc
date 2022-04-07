import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import org.jetbrains.kotlin.serialization.builtins.main
import sdksetup.SDKSetupFirstStepPanel
import java.awt.CardLayout
import javax.swing.JPanel

class OneSignalToolWindowFactory : ToolWindowFactory, OneSignalStepListener {

    private var project: Project? = null
    private var toolWindow: ToolWindow? = null
    private var mainPanel: JPanel? = null

    /**
     * Create the tool window content.
     *
     * @param project    current project
     * @param toolWindow current tool window
     */
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        this.project = project
        this.toolWindow = toolWindow

//        mainPanel = JPanel(CardLayout()).apply {
//            add(WelcomeScreenPanel(this@OneSignalToolWindowFactory))
//            add(SDKSetupFirstStepPanel(this@OneSignalToolWindowFactory))
//        }
        val contentFactory = ContentFactory.SERVICE.getInstance()
        val content = contentFactory.createContent(SDKSetupFirstStepPanel(project, this@OneSignalToolWindowFactory), "", false)
        toolWindow.contentManager.addContent(content)
    }

    private fun addContent(panel: JPanel) {

    }

    override fun onNextStep() {
        project?.let {
            showNotification(it, "Navigating to next panel")
        }
    }

    private fun showNotification(project: Project, message: String) {
        NotificationGroup("someID", NotificationDisplayType.BALLOON)
            .createNotification(
                "OneSignal plugin:",
                message,
                NotificationType.WARNING,
                null
            ).notify(project)
    }
//        project.let {
//            it.showNotification("Path: ${it.basePath}")
//
//            val basePath = it.basePath
//
//            val projectBuildGradle = File("$basePath/build.gradle")
//
//            var content: String = projectBuildGradle.readText()
//
//
//            val pluginRegex = "gradlePluginPortal()".toRegex()
//
//            it.showNotification("Path: ${pluginRegex.find(content)}")
//
//            content = content.replace("mavenCentral()", "mavenCentral()\n\t\tgradlePluginPortal()")
//        }

//        val devices = event.project?.let {
//            AndroidSdkUtils.getDebugBridge(it)?.devices
//        }

//            projectBuildGradle.writeText(content)


//            it.showNotification("content: $content")

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