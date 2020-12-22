package adventofcode.year2020.day22

import java.util.*

object Task1 {
    fun getScoreForWinner(input: String): Int {
        val (player1, player2) = input.split("\n\n")
        return runGame(startDeckForPlayer(player1), startDeckForPlayer(player2))
    }

    private fun runGame(player1Deck: Deque<Int>, player2Deck: Deque<Int>): Int {
        while (player1Deck.isNotEmpty() && player2Deck.isNotEmpty()) {
            val player1FirstCard = player1Deck.pop()
            val player2FirstCard = player2Deck.pop()

            if (player1FirstCard > player2FirstCard) {
                player1Deck.addLast(player1FirstCard)
                player1Deck.addLast(player2FirstCard)
            } else {
                player2Deck.addLast(player2FirstCard)
                player2Deck.addLast(player1FirstCard)
            }
        }

        val winningDeck = if (player1Deck.isNotEmpty()) player1Deck else player2Deck
        return winningScore(winningDeck)
    }
}

object Task2 {
    fun getScoreForWinner(input: String): Int {
        val (player1, player2) = input.split("\n\n")
        return runGame(startDeckForPlayer(player1), startDeckForPlayer(player2)).second
    }

    private fun runGame(player1Deck: Deque<Int>, player2Deck: Deque<Int>): Pair<Int, Int> {
        val previousRounds = mutableListOf<Pair<Deque<Int>, Deque<Int>>>()

        while (player1Deck.isNotEmpty() && player2Deck.isNotEmpty()) {
            if (matchesPreviousRound(previousRounds, player1Deck, player2Deck)) {
                // Player 1 wins if the rounds repeat, to avoid infinite recursion
                return Pair(1, winningScore(player1Deck))
            } else {
                previousRounds += Pair(LinkedList(player1Deck), LinkedList(player2Deck))

                val player1FirstCard = player1Deck.pop()
                val player2FirstCard = player2Deck.pop()

                if (player1Deck.size >= player1FirstCard && player2Deck.size >= player2FirstCard) {
                    // Recursive game
                    val player1RecursiveDeck = LinkedList(player1Deck.take(player1FirstCard))
                    val player2RecursiveDeck = LinkedList(player2Deck.take(player2FirstCard))

                    val winningPlayerAndScore = runGame(player1RecursiveDeck, player2RecursiveDeck)

                    if (winningPlayerAndScore.first == 1) {
                        player1Deck.addLast(player1FirstCard)
                        player1Deck.addLast(player2FirstCard)
                    } else {
                        player2Deck.addLast(player2FirstCard)
                        player2Deck.addLast(player1FirstCard)
                    }
                } else {
                    // The winner of the game is the player with the highest card
                    if (player1FirstCard > player2FirstCard) {
                        player1Deck.addLast(player1FirstCard)
                        player1Deck.addLast(player2FirstCard)
                    } else {
                        player2Deck.addLast(player2FirstCard)
                        player2Deck.addLast(player1FirstCard)
                    }
                }
            }
        }

        return if (player1Deck.isNotEmpty()) Pair(1, winningScore(player1Deck)) else Pair(2, winningScore(player2Deck))
    }

    private fun matchesPreviousRound(previousRounds: List<Pair<Deque<Int>, Deque<Int>>>, player1Deck: Deque<Int>, player2Deck: Deque<Int>): Boolean {
        return previousRounds.contains(Pair(player1Deck, player2Deck))
    }
}

private fun startDeckForPlayer(input: String): Deque<Int> {
    val cards = input.lines().drop(1).map { it.toInt() }

    val deque = LinkedList<Int>()
    deque.addAll(cards)

    return deque
}

private fun winningScore(deck: Deque<Int>): Int {
    return deck.reversed().mapIndexed { index, card -> card * (index + 1) }.sum()
}