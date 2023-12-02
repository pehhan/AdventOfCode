package adventofcode.year2023.day1

object Task1 {
    fun sum(input: String): Int {
        return input
            .lines()
            .fold(0) { sum, line ->
                sum + line.calibrationValue()
            }
    }
}

object Task2 {
    fun sum(input: String): Int {
        return input
            .lines()
            .fold(0) { sum, line ->
                sum + line.lettersReplacedByDigits().calibrationValue()
            }
    }

    private fun String.lettersReplacedByDigits(): String {
        return fold("") { line, char ->
            val newLine = line + char
            val replacement = replacements.filter { newLine.contains(it.key) }.entries.firstOrNull()

            if (replacement != null) {
                newLine.replace(replacement.key, replacement.value)
            } else {
                newLine
            }
        }
    }

    // Bit funky but works, keep last letter of written out digit
    // since it may be the start of another written out digit.
    private val replacements = mapOf(
        "one" to "1e",
        "two" to "2o",
        "three" to "3e",
        "four" to "4r",
        "five" to "5e",
        "six" to "6x",
        "seven" to "7n",
        "eight" to "8t",
        "nine" to "9e"
    )
}

private fun String.calibrationValue(): Int {
    val number = first { it.isDigit() }.toString() + last { it.isDigit() }.toString()
    return number.toInt()
}