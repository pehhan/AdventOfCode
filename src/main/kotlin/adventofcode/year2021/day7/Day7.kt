package adventofcode.year2021.day7

import kotlin.math.abs

object Task1 {
    fun fuel(input: String) = fuel(input, ::cost)

    private fun cost(positions: Int) = positions
}

object Task2 {
    fun fuel(input: String) = fuel(input, ::cost)

    private fun cost(positions: Int) = (0..positions).sum()
}

private fun fuel(input: String, cost: (Int) -> Int): Int {
    val positions = input.split(",").map { it.toInt() }

    val minPosition = positions.minOf { it }
    val maxPosition = positions.maxOf { it }

    return (minPosition..maxPosition)
        .map { position -> positions.map { cost(abs(it - position)) }.sum() }
        .minOf { it }
}