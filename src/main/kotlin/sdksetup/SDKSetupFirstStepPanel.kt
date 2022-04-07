package sdksetup

import view.MultilineLabel
import OneSignalStep
import OneSignalStepListener
import com.intellij.openapi.project.Project
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JButton
import javax.swing.JPanel

class SDKSetupFirstStepPanel(
    private val project: Project,
    private val stepListener: OneSignalStepListener
) : JPanel(),
    OneSignalStep {

    private val controller = SDKSetupFirstStepController()
    private val instructionString = """
        OneSignal SDK needs the following changes in your build.gradle file

        buildscript {

            repositories {
                ...
                gradlePluginPortal()
            }

            dependencies {

               classpath 'com.onesignal:onesignal-gradle-plugin:[0.8.1, 0.99.99]'
            }
       }

       and in your application build.gradle

       dependencies {
            implementation('com.onesignal:OneSignal:4.6.3')
       }
       
       Be sure that before next button is clicked your Gradle is sync
    """
    private var instructionsLabel: MultilineLabel
    private var nextButton: JButton

    init {
        instructionsLabel = MultilineLabel(instructionString)
        nextButton = JButton("Next")

        initScreenPanel()
    }

    private fun initScreenPanel() {
        layout = GridBagLayout()

        var bagConstraints = GridBagConstraints()

        // Instructions Label

        bagConstraints.gridy = 0
        bagConstraints.fill = GridBagConstraints.HORIZONTAL
        bagConstraints.weightx = 1.0
        bagConstraints.weighty = 1.0

        add(instructionsLabel, bagConstraints)

        // Next Button

        bagConstraints.gridy = 1
        bagConstraints.gridx = 1
        bagConstraints.weightx = 1.0
        bagConstraints.weighty = 0.1

        add(nextButton, bagConstraints)

        initListeners()
    }

    private fun initListeners() {
        nextButton.addActionListener {
//            stepListener.onNextStep()
            controller.addSDKToBuildGradle(project)
        }
    }

    override fun getContent() = this
}