import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import org.jetbrains.eval4j.boolean
import org.jetbrains.kotlin.serialization.builtins.main
import sdksetup.SDKSetupFirstStepPanel
import java.awt.CardLayout
import javax.swing.JComponent
import javax.swing.JFrame
import javax.swing.JPanel

class OneSignalToolWindowFactory : ToolWindowFactory, OneSignalStepListener {

    private var project: Project? = null
    private var toolWindow: ToolWindow? = null
    private var mainPanel: JPanel? = null
    private var mainCardLayout = CardLayout()

    private var sdkSetupSteps = linkedMapOf<String, OneSignalStep>()
    private var sdkSetupStepIndex = 0
    private var welcomeKey = "welcome_panel"

    /**
     * Create the tool window content.
     *
     * @param project    current project
     * @param toolWindow current tool window
     */
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        this.project = project
        this.toolWindow = toolWindow

        // If basePath is null add step to get basePath
        val firstStepPanel = SDKSetupFirstStepPanel(project.basePath!!, project, this@OneSignalToolWindowFactory)
        sdkSetupSteps["first_step_panel"] = firstStepPanel

        val welcomePanel = WelcomeScreenPanel(this@OneSignalToolWindowFactory)

        this.mainPanel = JPanel(mainCardLayout).apply {
            add(welcomePanel, welcomeKey)

            sdkSetupSteps.entries.forEach {
                add(it.value as JComponent, it.key)
            }
        }

        val contentFactory = ContentFactory.SERVICE.getInstance()
        val content = contentFactory.createContent(mainPanel, "", false)
        toolWindow.contentManager.addContent(content)
    }

    override fun onNextStep() {
        project?.let {
            showNotification(it, "Navigating to next panel")
        }

        var index = 0
        val keysIterator = sdkSetupSteps.keys.iterator()
        while (keysIterator.hasNext()) {
            if (index == sdkSetupStepIndex) {
                sdkSetupStepIndex++
                showPanel(keysIterator.next())
                break
            }
            index++
        }
    }

    private fun showPanel(panelName: String) {
        mainCardLayout.show(mainPanel, panelName);
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
}