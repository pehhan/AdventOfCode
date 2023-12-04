package adventofcode.year2023.day4

import kotlin.math.pow

data class Card(val id: Int, val winningNumbers: Set<Int>, val myNumbers: Set<Int>) {

    fun points(): Int {
        val matchingNumbers = myNumbers.intersect(winningNumbers)

        return if (matchingNumbers.isEmpty()) {
            0
        } else {
            2f.pow(matchingNumbers.size - 1).toInt()
        }
    }
}

object Task1 {
    fun totalPoints(input: String): Int {
        return input
            .lines()
            .map { it.toCard() }
            .sumOf { it.points() }
    }
}

fun String.toCard(): Card {
    val id = substringBefore(":").split(" ").last().toInt()

    val winningNumbers = this
        .replace("  ", " ")
        .substringAfter(": ")
        .substringBefore(" |")
        .split(" ")
        .map { it.toInt() }
        .toSet()

    val myNumbers = this
        .replace("  ", " ")
        .substringAfter("| ")
        .split(" ")
        .map { it.toInt() }
        .toSet()

    return Card(id, winningNumbers, myNumbers)
}