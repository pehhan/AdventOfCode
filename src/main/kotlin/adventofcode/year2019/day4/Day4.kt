package adventofcode.year2019.day4

object Task1 {
    fun numberOfMatchingPasswords(range: IntRange): Int {
        return range.map { it.toString() }.count { passwordMatches(it) }
    }

    private fun passwordMatches(password: String): Boolean {
        return hasAtLeastDoubleDigits(password) && doesNotDecrease(password)
    }

    private fun hasAtLeastDoubleDigits(password: String): Boolean {
        return password.groupBy { it }.any { it.value.size >= 2 }
    }
}

object Task2 {
    fun numberOfMatchingPasswords(range: IntRange): Int {
        return range.map { it.toString() }.count { passwordMatches(it) }
    }

    private fun passwordMatches(password: String): Boolean {
        return hasExactlyDoubleDigits(password) && doesNotDecrease(password)
    }

    private fun hasExactlyDoubleDigits(password: String): Boolean {
        return password.groupBy { it }.any { it.value.size == 2 }
    }
}

private fun doesNotDecrease(password: String): Boolean {
    for (i in 0 until password.length - 1) {
        val current = password[i]
        val next = password[i + 1]

        if (next < current) return false
    }

    return true
}