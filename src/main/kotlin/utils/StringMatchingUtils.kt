import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project

fun String.appendStringByMatch(match: String, append: String, indentation: String, project:Project): String {
    val appendRegex = append.toRegex()
    val appendMatch = appendRegex.find(this)?.range

    showNotification(project, "Append string: $append match: $appendMatch")
    return if (appendMatch == null) {
        val matchRegex = match.toRegex()
        val regexMatch = matchRegex.find(this)?.range
        showNotification(project, "Match string: $match match: $regexMatch")
        if (regexMatch != null) {
            showNotification(project, "Making replacement \"$match$indentation$append\"")
            val result = "${this.substring(0, regexMatch.last + 1)}$indentation$append${this.substring(regexMatch.last + 1, this.length)}"
            showNotification(project, result)
            result
        } else {
            this
        }
    } else {
        this
    }
}

fun showNotification(project: Project, message: String) {
    NotificationGroup("someID", NotificationDisplayType.BALLOON)
        .createNotification(
            "OneSignal plugin:",
            message,
            NotificationType.WARNING,
            null
        ).notify(project)
}