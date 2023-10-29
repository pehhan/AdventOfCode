package adventofcode.year2018.day2

import java.lang.IllegalArgumentException

object Task1 {
    fun checksum(input: String): Int {
        return input
            .lines()
            .map { it.groupingBy { char -> char }.eachCount() }
            .checksum()
    }

    private fun List<Map<Char, Int>>.checksum(): Int {
        return numberByCount(2) * numberByCount(3)
    }

    private fun List<Map<Char, Int>>.numberByCount(count: Int): Int {
        return map { group -> group.filter { it.value == count } }.filter { it.values.isNotEmpty() }.size
    }
}

object Task2 {
    fun commonLetters(input: String): String {
        val ids = input.lines()

        for (id in ids) {
            for (otherId in ids.filterNot { it == id }) {
                if (id.numberOfDifferentCharacters(otherId) == 1) {
                    return id.commonCharacters(otherId)
                }
            }
        }

        throw IllegalArgumentException("Could not find ids")
    }

    private fun String.numberOfDifferentCharacters(other: String): Int {
        var count = 0

        for (index in this.indices) {
            if (this[index] != other[index]) {
                count++
            }
        }

        return count
    }

    private fun String.commonCharacters(other: String): String {
        var result = ""

        for (index in this.indices) {
            if (this[index] == other[index]) {
                result += this[index]
            }
        }

        return result
    }
}