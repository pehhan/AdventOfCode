package adventofcode.year2023.day7

import adventofcode.year2023.day7.Type.FiveOfAKind
import adventofcode.year2023.day7.Type.FourOfAKind
import adventofcode.year2023.day7.Type.FullHouse
import adventofcode.year2023.day7.Type.HighCard
import adventofcode.year2023.day7.Type.OnePair
import adventofcode.year2023.day7.Type.ThreeOfAKind
import adventofcode.year2023.day7.Type.TwoPair

enum class Card(val value: Int) {
    Ace(14),
    King(13),
    Queen(12),
    Jack(11),
    Ten(10),
    Nine(9),
    Eight(8),
    Seven(7),
    Six(6),
    Five(5),
    Four(4),
    Three(3),
    Deuce(2),
    Joker(1)
}

enum class Type(val value: Int) {
    FiveOfAKind(7),
    FourOfAKind(6),
    FullHouse(5),
    ThreeOfAKind(4),
    TwoPair(3),
    OnePair(2),
    HighCard(1);

    fun beats(other: Type): Boolean {
        return value > other.value
    }
}

data class Hand(val cards: List<Card>, val bid: Int) {

    fun beats(otherHand: Hand, useJokers: Boolean): Boolean {
        val type = type(useJokers)
        val otherType = otherHand.type(useJokers)

        return if (type == otherType) {
            val values = cards.map { it.value }
            val otherValues = otherHand.cards.map { it.value }

            val pair = values
                .zip(otherValues)
                .first { it.first != it.second }

            pair.first > pair.second
        } else {
            type.beats(otherType)
        }
    }

    private fun type(useJokers: Boolean): Type {
        return if (useJokers) {
            typeWithJokers()
        } else {
            typeWithoutJokers()
        }
    }

    private fun typeWithoutJokers(): Type {
        return when {
            isFiveOfAKind() -> FiveOfAKind
            isFourOfAKind() -> FourOfAKind
            isFullHouse() -> FullHouse
            isThreeOfAKind() -> ThreeOfAKind
            isTwoPair() -> TwoPair
            isOnePair() -> OnePair
            else -> HighCard
        }
    }

    private fun typeWithJokers(): Type {
        return when (val numberOfJokers = numberOfJokers()) {
            5, 4 -> FiveOfAKind
            3 -> typeWithThreeJokers()
            2 -> typeWithTwoJokers()
            1 -> typeWithOneJoker()
            0 -> typeWithoutJokers()
            else -> throw IllegalArgumentException("Unexpected number of jokers: $numberOfJokers")
        }
    }

    private fun typeWithThreeJokers(): Type {
        return when {
            isOnePair() -> FiveOfAKind
            else -> FourOfAKind
        }
    }

    private fun typeWithTwoJokers(): Type {
        return when {
            isThreeOfAKind() -> FiveOfAKind
            isTwoPair() -> FourOfAKind
            else -> ThreeOfAKind
        }
    }

    private fun typeWithOneJoker(): Type {
        return when {
            isFourOfAKind() -> FiveOfAKind
            isThreeOfAKind() -> FourOfAKind
            isTwoPair() -> FullHouse
            isOnePair() -> ThreeOfAKind
            else -> OnePair
        }
    }

    private fun isFiveOfAKind(): Boolean {
        return isXOfAKind(5)
    }

    private fun isFourOfAKind(): Boolean {
        return isXOfAKind(4)
    }

    private fun isFullHouse(): Boolean {
        return isThreeOfAKind() && isOnePair()
    }

    private fun isThreeOfAKind(): Boolean {
        return isXOfAKind(3)
    }

    private fun isTwoPair(): Boolean {
        return groupedCards().filter { it.value == 2 }.size == 2
    }

    private fun isOnePair(): Boolean {
        return isXOfAKind(2)
    }

    private fun isXOfAKind(x: Int): Boolean {
        return groupedCards().any { it.value == x }
    }

    private fun groupedCards(): Map<Card, Int> {
        return cards.groupingBy { it }.eachCount()
    }

    private fun numberOfJokers(): Int {
        return cards.count { it == Card.Joker }
    }
}

class HandComparator(private val useJokers: Boolean) : Comparator<Hand> {

    override fun compare(hand1: Hand, hand2: Hand): Int {
        return if (hand1.beats(hand2, useJokers)) 1 else -1
    }
}

object Task1 {
    fun totalWinnings(input: String): Int {
        return totalWinnings(input, useJokers = false)
    }
}

object Task2 {
    fun totalWinnings(input: String): Int {
        return totalWinnings(input, useJokers = true)
    }
}

private fun totalWinnings(input: String, useJokers: Boolean): Int {
    return input
        .lines()
        .map {
            it.toHand(useJokers)
        }
        .sortedWith(HandComparator(useJokers))
        .mapIndexed { index, hand ->
            (index + 1) * hand.bid
        }
        .sum()
}

private fun String.toHand(useJokers: Boolean): Hand {
    val cards = split(" ").first().map {
        when (it) {
            'A' -> Card.Ace
            'K' -> Card.King
            'Q' -> Card.Queen
            'J' -> if (useJokers) Card.Joker else Card.Jack
            'T' -> Card.Ten
            '9' -> Card.Nine
            '8' -> Card.Eight
            '7' -> Card.Seven
            '6' -> Card.Six
            '5' -> Card.Five
            '4' -> Card.Four
            '3' -> Card.Three
            '2' -> Card.Deuce
            else -> throw IllegalArgumentException("Incorrect card: $it")
        }
    }.toList()

    val bid = split(" ").last().toInt()

    return Hand(cards, bid)
}