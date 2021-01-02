package adventofcode.year2015.day10

import java.lang.StringBuilder

object Task {
    fun lengthOfResult(input: String, repetitions: Int): Int {
        var result = input

        repeat(repetitions) {
            result = playLookAndSay(result)
        }

        return result.length
    }

    private fun playLookAndSay(input: String): String {
        val sb = StringBuilder()
        var i = 0

        while (i < input.length) {
            val char = input[i]
            var count = 1

            while (i + count < input.length && input[i + count] == char) {
                count++
            }

            sb.append(count)
            sb.append(char)

            i += count
        }

        return sb.toString()
    }
}