package adventofcode.year2015.day8

data class StringCount(val literalCount: Int, val memoryCount: Int) {

    companion object {
        fun from(str: String): StringCount {
            val singleBackslashes = str.countMatches("\\\\")
            val quotes = str.countMatches("\\\"")
            val asciiCharacters = "\\\\x[0-9a-f]{2}".toRegex().findAll(str).count()

            val literalCount = str.length
            val memoryCount = literalCount - singleBackslashes - quotes - (asciiCharacters * 3) - 2

            return StringCount(literalCount, memoryCount)
        }
    }
}

object Task1 {
    fun number(input: String): Int {
        return input
            .lines()
            .map {  StringCount.from(it) }
            .let { list -> list.sumOf { it.literalCount } - list.sumOf { it.memoryCount } }
    }
}

object Task2 {
    fun number(input: String): Int {
        val originalSum = input
            .lines()
            .map { StringCount.from(it) }
            .sumOf { it.literalCount }

        val encodedSum = input
            .lines()
            .map { encodeString(it) }
            .map { StringCount.from(it) }
            .sumOf { it.literalCount }

        return encodedSum - originalSum
    }

    private fun encodeString(str: String): String {
        // This solves the problem, but does not actually encode the string properly since the first
        // replace() will add more backslashes which might cause the following replace():s to replace
        // more backslashes than in the original string. Since we are only interested in the new length
        // in this problem it does not actually matter what we replace the values with, as long as the
        // new length of the string is correct.
        return str.replace("\\\\", "____").replace("\\\"", "____").replace("\\x", "___").replaceFirst("\"", "\"\\\"").replaceLast("\"", "\\\"\"")
    }
}

private fun String.countMatches(str: String): Int {
    return split(str).dropLastWhile { it.isEmpty() }.toTypedArray().size - 1
}

private fun String.replaceLast(oldValue: String, newValue: String): String {
    val index = lastIndexOf(oldValue)
    return if (index == -1) this else substring(0, index) + newValue + substring(index + oldValue.length)
}