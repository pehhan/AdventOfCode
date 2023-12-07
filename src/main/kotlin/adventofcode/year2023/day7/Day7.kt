package adventofcode.year2023.day7

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
    Deuce(2)
}

enum class Type(val value: Int) {
    FiveOfAKind(7),
    FourOfAKind(6),
    FullHouse(5),
    ThreeOfAKind(4),
    TwoPair(3),
    OnePair(2),
    HighCard(1);

    fun beats(type: Type): Boolean {
        return value > type.value
    }
}

data class Hand(val cards: List<Card>, val bid: Int) {

    fun beats(otherHand: Hand, treatJAsJokers: Boolean): Boolean {
        val type = type(treatJAsJokers)
        val otherType = otherHand.type(treatJAsJokers)

        return if (type == otherType) {
            val values = cards.toValues(treatJAsJokers)
            val otherValues = otherHand.cards.toValues(treatJAsJokers)

            val pair = values
                .zip(otherValues)
                .first { it.first != it.second }

            pair.first > pair.second
        } else {
            type.beats(otherType)
        }
    }

    private fun type(treatJAsJokers: Boolean): Type {
        return if (treatJAsJokers) {
            typeWithJokers()
        } else {
            typeWithoutJokers()
        }
    }

    private fun typeWithoutJokers(): Type {
        return when {
            isFiveOfAKind() -> Type.FiveOfAKind
            isFourOfAKind() -> Type.FourOfAKind
            isFullHouse() -> Type.FullHouse
            isThreeOfAKind() -> Type.ThreeOfAKind
            isTwoPair() -> Type.TwoPair
            isOnePair() -> Type.OnePair
            else -> Type.HighCard
        }
    }

    private fun typeWithJokers(): Type {
        return when (val numberOfJokers = numberOfJokers()) {
            5, 4 -> {
                Type.FiveOfAKind
            }

            3 -> {
                if (isOnePair()) {
                    Type.FiveOfAKind
                } else {
                    Type.FourOfAKind
                }
            }

            2 -> {
                if (isThreeOfAKind()) {
                    Type.FiveOfAKind
                } else if (isTwoPair()) {
                    Type.FourOfAKind
                } else {
                    Type.ThreeOfAKind
                }
            }

            1 -> {
                if (isFourOfAKind()) {
                    Type.FiveOfAKind
                } else if (isThreeOfAKind()) {
                    Type.FourOfAKind
                } else if (isTwoPair()) {
                    Type.FullHouse
                } else if (isOnePair()) {
                    Type.ThreeOfAKind
                } else {
                    Type.OnePair
                }
            }

            0 -> {
                typeWithoutJokers()
            }

            else -> {
                throw IllegalArgumentException("Unexpected number of jokers: $numberOfJokers")
            }
        }
    }

    private fun isFiveOfAKind(): Boolean {
        return groupedCards().any { it.value == 5 }
    }

    private fun isFourOfAKind(): Boolean {
        return groupedCards().any { it.value == 4 }
    }

    private fun isFullHouse(): Boolean {
        return groupedCards().any { it.value == 3 } && groupedCards().any { it.value == 2 }
    }

    private fun isThreeOfAKind(): Boolean {
        return groupedCards().any { it.value == 3 }
    }

    private fun isTwoPair(): Boolean {
        return groupedCards().filter { it.value == 2 }.size == 2
    }

    private fun isOnePair(): Boolean {
        return groupedCards().any { it.value == 2 }
    }

    private fun groupedCards(): Map<Card, Int> {
        return cards.groupingBy { it }.eachCount()
    }

    private fun numberOfJokers(): Int {
        return cards.count { it == Card.Jack }
    }

    private fun List<Card>.toValues(treatJAsJokers: Boolean): List<Int> {
        return map { it.value }.map { if (treatJAsJokers) replaceJackWithLowestValue(it) else it }
    }

    private fun replaceJackWithLowestValue(value: Int): Int {
        return if (value == Card.Jack.value) Card.Deuce.value - 1 else value
    }
}

class HandComparator(private val treatJAsJokers: Boolean) : Comparator<Hand> {

    override fun compare(hand1: Hand, hand2: Hand): Int {
        return if (hand1.beats(hand2, treatJAsJokers)) 1 else -1
    }
}

object Task1 {
    fun totalWinnings(input: String): Int {
        return totalWinnings(input, treatJAsJokers = false)
    }
}

object Task2 {
    fun totalWinnings(input: String): Int {
        return totalWinnings(input, treatJAsJokers = true)
    }
}

private fun totalWinnings(input: String, treatJAsJokers: Boolean): Int {
    return input
        .lines()
        .map {
            it.toHand()
        }
        .sortedWith(HandComparator(treatJAsJokers))
        .mapIndexed { index, hand ->
            (index + 1) * hand.bid
        }
        .sum()
}

private fun String.toHand(): Hand {
    val cards = split(" ").first().map {
        when (it) {
            'A' -> Card.Ace
            'K' -> Card.King
            'Q' -> Card.Queen
            'J' -> Card.Jack
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