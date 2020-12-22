package adventofcode.year2020.day22

import java.util.*

object Task1 {
    fun getScoreForWinner(input: String): Int {
        val (player1, player2) = input.split("\n\n")

        return runGame(startDeckForPlayer(player1), startDeckForPlayer(player2))
    }

    private fun startDeckForPlayer(input: String): Deque<Int> {
        val cards = input.lines().drop(1).map { it.toInt() }

        val deque = LinkedList<Int>()
        deque.addAll(cards)

        return deque
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
        return winningDeck.reversed().mapIndexed { index, card -> card * (index + 1) }.sum()
    }
}