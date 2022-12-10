package adventofcode.year2022.day9

import adventofcode.year2022.day9.Direction.*
import kotlin.math.abs

object Task1 {
    fun visited(input: String) = visited(input, tail(1))
}

object Task2 {
    fun visited(input: String) = visited(input, tail(9))
}

private fun tail(n: Int) = (0 until n).map { Position(0, 0) }

private fun visited(input: String, tail: List<Position>): Int {
    return input
        .lines()
        .map { it.toMotion() }
        .fold(State(tail = tail)) { state, motion ->
            apply(state, motion)
        }
        .visited
        .size
}

enum class Direction {
    Left, Right, Up, Down
}

data class Motion(val direction: Direction, val steps: Int)

data class Position(val x: Int, val y: Int)

data class State(
    val head: Position = Position(0, 0),
    val tail: List<Position>,
    val visited: Set<Position> = emptySet()
)

fun apply(previousState: State, motion: Motion): State {
    return (0 until motion.steps).fold(previousState) { state, _ ->
        val newHead = headAfterOneStepInDirection(state.head, motion.direction)

        val headAndKnots = state.tail.fold(Pair(newHead, emptyList<Position>())) { headAndKnots, tail ->
            val newTail = tailForHead(headAndKnots.first, tail)

            Pair(newTail, headAndKnots.second + newTail)
        }

        State(newHead, headAndKnots.second, state.visited + headAndKnots.second.last())
    }
}

private fun headAfterOneStepInDirection(head: Position, direction: Direction): Position {
    val newX = when (direction) {
        Left -> head.x - 1
        Right -> head.x + 1
        Up, Down -> head.x
    }

    val newY = when (direction) {
        Left, Right -> head.y
        Up -> head.y + 1
        Down -> head.y - 1
    }

    return Position(newX, newY)
}

private fun tailForHead(head: Position, tail: Position): Position {
    val newX = tailPosition(head.x, head.y, tail.x, tail.y)
    val newY = tailPosition(head.y, head.x, tail.y, tail.x)

    return Position(newX, newY)
}

private fun tailPosition(headA: Int, headB: Int, tailA: Int, tailB: Int): Int {
    return when {
        headA - tailA > 1 -> tailA + 1
        tailA - headA > 1 -> tailA - 1
        abs(headB - tailB) > 1 -> headA
        else -> tailA
    }
}

fun String.toMotion() = Motion(split(" ").first().toDirection(), split(" ").last().toInt())

fun String.toDirection(): Direction {
    return when (this) {
        "L" -> Left
        "R" -> Right
        "U" -> Up
        "D" -> Down
        else -> error("Invalid direction: $this")
    }
}