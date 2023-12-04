package adventofcode.year2023.day4

import kotlin.math.pow

data class Card(val id: Int, val winningNumbers: Set<Int>, val myNumbers: Set<Int>) {

    fun matchingNumbers(): Int {
        return myNumbers.intersect(winningNumbers).size
    }

    fun points(): Int {
        val matchingNumbers = matchingNumbers()
        return if (matchingNumbers == 0) {
            0
        } else {
            2f.pow(matchingNumbers - 1).toInt()
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

object Task2 {
    fun totalCards(input: String): Int {
        val cards = input
            .lines()
            .map { it.toCard() }

        val totalCards = cards
            .associate { it.id to 1 }
            .toMutableMap()

        for (card in cards) {
            val copies = totalCards[card.id] ?: 0
            val matchingNumbers = card.matchingNumbers()

            repeat(copies) {
                for (id in card.id + 1..card.id + matchingNumbers) {
                    if (id <= cards.size) {
                        totalCards[id] = (totalCards[id] ?: 0) + 1
                    }
                }
            }
        }

        return totalCards.values.sum()
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