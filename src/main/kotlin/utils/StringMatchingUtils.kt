fun String.appendStringByMatch(match: String, append: String, indentation: String): String {
    val appendRegex = append.toRegex()
    val appendMatch = appendRegex.find(this)?.range

    return if (appendMatch == null) {
        val matchRegex = match.toRegex()
        val regexMatch = matchRegex.find(this)?.range

        if (regexMatch != null) {
            this.replace(match, "$match$indentation$append")
        } else {
            this
        }
    } else {
        this
    }
}