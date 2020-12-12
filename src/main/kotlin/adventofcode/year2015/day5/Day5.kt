package adventofcode.year2015.day5

object Task1 {
    fun numberOfNiceStrings(input: String): Int {
        return input.lines().count { isNice(it) }
    }

    private fun isNice(str: String): Boolean {
        return hasAtLeastThreeVowels(str) && hasLetterThatAppearsTwiceInARow(str) && !hasNaughtyPart(str)
    }

    private fun hasAtLeastThreeVowels(str: String): Boolean {
        return str.count { it in listOf('a', 'e', 'i', 'o', 'u') } >= 3
    }

    private fun hasLetterThatAppearsTwiceInARow(str: String): Boolean {
        return str.matches(".*([a-z])\\1+.*".toRegex())
    }

    private fun hasNaughtyPart(str: String): Boolean {
        return str.contains("ab") || str.contains("cd") || str.contains("pq") || str.contains("xy")
    }
}

object Task2 {
    fun numberOfNiceStrings(input: String): Int {
        return input.lines().count { isNice(it) }
    }

    private fun isNice(str: String): Boolean {
        return hasPairOfTwoLettersThatAppearTwiceWithoutOverlapping(str) && hasAtLeastOneLetterThatRepeatsWithOneLetterBetween(str)
    }

    private fun hasPairOfTwoLettersThatAppearTwiceWithoutOverlapping(str: String): Boolean {
        for (i in 0 until str.length - 2) {
            val pair = str[i] + str[i + 1]
            if (str.drop(i + 2).contains(pair)) return true
        }

        return false
    }

    private fun hasAtLeastOneLetterThatRepeatsWithOneLetterBetween(str: String): Boolean {
        for (i in 0 until str.length - 2) {
            if (str[i] == str[i + 2]) return true
        }

        return false
    }
}

private operator fun Char.plus(other: Char): String {
    return "$this$other"
}