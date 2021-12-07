package adventofcode.year2021.day7

import kotlin.math.abs

object Task1 {
    fun fuel(input: String) = fuel(input, ::cost)

    private fun cost(positions: Int) = positions
}

object Task2 {
    fun fuel(input: String) = fuel(input, ::cost)

    private fun cost(positions: Int) = positions * (positions + 1) / 2
}

private fun fuel(input: String, cost: (Int) -> Int): Int {
    val positions = input.split(",").map { it.toInt() }

    val minPosition = positions.minOf { it }
    val maxPosition = positions.maxOf { it }

    return (minPosition..maxPosition)
        .map { position -> positions.sumOf { cost(abs(it - position)) } }
        .minOf { it }
}