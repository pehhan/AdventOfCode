package adventofcode.year2024.day1

import kotlin.math.abs

object Task1 {
    fun distance(input: String): Int {
        val left = sortedColumn(input, index = 0)
        val right = sortedColumn(input, index = 1)

        return left.zip(right) { a, b ->
            abs(a - b)
        }.sum()
    }
}

object Task2 {
    fun similarity(input: String): Int {
        val left = sortedColumn(input, index = 0)
        val right = sortedColumn(input, index = 1)

        return left.fold(0) { result, number ->
            result + number * right.count { it == number }
        }
    }
}

private fun sortedColumn(input: String, index: Int): List<Int> {
    return input
        .lines()
        .map {
            it.split("\\s+".toRegex())
        }
        .map {
            it[index]
        }
        .map {
            it.toInt()
        }
        .sorted()
}