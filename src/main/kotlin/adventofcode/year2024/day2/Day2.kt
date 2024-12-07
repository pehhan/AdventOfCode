package adventofcode.year2024.day2

import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

object Task1 {
    fun safeReports(input: String): Int {
        return input
            .lines()
            .map { it.split(" ") }
            .map { list -> list.map { it.toInt() } }
            .map { it.zipWithNext { a, b -> a - b } }
            .count { it.isSafe() }
    }
}

object Task2 {

    fun safeReports(input: String): Int {
        return input
            .lines()
            .map { it.split(" ") }
            .map { list -> list.map { it.toInt() } }
            .map { list ->
                list
                    .mapIndexed { index, _ ->
                        val from = max(0, index)
                        val to = min(list.size, index + 1)
                        list.subList(0, from) + list.subList(to, list.size)
                    }
                    .map { it.zipWithNext { a, b -> a - b } }
            }
            .map { lists ->
                lists.map { list ->
                    list.isSafe()
                }
            }
            .count {
                it.any { it }
            }
    }
}

private fun List<Int>.isSafe(): Boolean {
    return all { it.absoluteValue in 1..3 } && (all { it > 0 } || all { it < 0 })
}