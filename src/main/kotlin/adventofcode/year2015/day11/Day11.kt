package adventofcode.year2015.day11

import java.lang.StringBuilder

object Task1 {
    fun nextPassword(previousPassword: String): String {

        var nextPassword = previousPassword
        do {
            nextPassword = incrementPassword(nextPassword)
        } while (!isPasswordValid(nextPassword))

        return nextPassword
    }

    private fun incrementPassword(password: String): String {
        val sb = StringBuilder()
        val reversedPassword = password.reversed()

        for (i in reversedPassword.indices) {
            val char = reversedPassword[i]
            if (char == 'z') {
                sb.append('a')
            } else {
                sb.append(char + 1)
                break
            }
        }

        sb.append(reversedPassword.substring(sb.length))

        return sb.toString().reversed()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length == 8 &&
                hasNoIllegalLetters(password) &&
                hasThreeStraightIncreasingLetters(password) &&
                hasTwoDifferentNonOverlappingPairsOfLetters(password)
    }

    private fun hasNoIllegalLetters(password: String): Boolean {
        return !password.any { it == 'i' || it == 'o' || it == 'l' }
    }

    private fun hasThreeStraightIncreasingLetters(password: String): Boolean {
        for (i in 0 until password.length - 2) {
            if (password[i + 1] == password[i] + 1 && password[i + 2] == password[i + 1] + 1) return true
        }

        return false
    }

    private fun hasTwoDifferentNonOverlappingPairsOfLetters(password: String): Boolean {
        val pairs = mutableSetOf<Char>()
        var i = 0

        while (i < password.length - 1) {
            if (password[i] == password[i + 1]) {
                pairs += password[i]
                i += 2
            } else {
                i++
            }
        }

        return pairs.size >= 2
    }
}