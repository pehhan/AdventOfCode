package adventofcode.year2024.day2

import kotlin.math.absoluteValue

object Task1 {

    fun safeReports(input: String): Int {
        return input
            .lines()
            .map { it.split(" ") }
            .map { list -> list.map { it.toInt() } }
            .map { it.zipWithNext { a, b -> a - b } }
            .count { it.isSafe() }
    }

    private fun List<Int>.isSafe(): Boolean {
        return all { it.absoluteValue in 1..3 } && (all { it > 0 } || all { it < 0 })
    }
}