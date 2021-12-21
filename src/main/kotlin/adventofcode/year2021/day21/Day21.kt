package adventofcode.year2021.day21

import kotlin.math.min


data class Player(var position: Int, var score: Int = 0, var numberOfRolls: Int = 0) {

    companion object {
        fun parse(input: String): Player {
            val position = input.substringAfter(": ").toInt()
            return Player(position)
        }
    }
}

data class Game(val player1: Player, val player2: Player) {

    fun play() {
        var die = 0

        while (!hasEnded()) {
            die = roll(player1, die)
            if (hasEnded()) break
            die = roll(player2, die)
        }
    }

    private fun roll(player: Player, die: Int): Int {
        val die1 = nextDiceRoll(die, 1)
        val die2 = nextDiceRoll(die, 2)
        val die3 = nextDiceRoll(die, 3)

        player.position = (player.position + die1 + die2 + die3) % 10

        if (player.position == 0) {
            player.position = 10
        }

        player.score += player.position
        player.numberOfRolls += 3

        return die3
    }

    private fun nextDiceRoll(die: Int, steps: Int): Int {
        var next = (die + steps) % 100
        if (next == 0) {
            next = 100
        }
        return next
    }

    private fun hasEnded(): Boolean {
        return player1.score >= 1000 || player2.score >= 1000
    }
}

object Task1 {
    fun result(input: String): Int {
        val (player1, player2) = input.lines().map { Player.parse(it) }
        val game = Game(player1, player2)

        game.play()

        return min(player1.score, player2.score) * (player1.numberOfRolls + player2.numberOfRolls)
    }
}