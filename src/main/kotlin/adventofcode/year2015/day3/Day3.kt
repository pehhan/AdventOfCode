package adventofcode.year2015.day3

import adventofcode.year2015.day3.Direction.*
import java.lang.IllegalArgumentException

enum class Direction {
    North, South, East, West
}

data class Coordinate(val x: Int, val y: Int)

private fun toDirection(char: Char): Direction {
    return when (char) {
        '^' -> North
        'v' -> South
        '>' -> East
        '<' -> West
        else -> throw IllegalArgumentException("Unknown direction: $char")
    }
}

private fun housesThatReceivePresent(input: String): List<Coordinate> {
    val visited = listOf(Coordinate(0, 0))

    return input
        .map { toDirection(it) }
        .fold(visited) { list, direction -> list + newCoordinate(list.last(), direction) }
}

private fun newCoordinate(lastCoordinate: Coordinate, direction: Direction): Coordinate {
    return when (direction) {
        North -> Coordinate(lastCoordinate.x, lastCoordinate.y + 1)
        South -> Coordinate(lastCoordinate.x, lastCoordinate.y - 1)
        East -> Coordinate(lastCoordinate.x + 1, lastCoordinate.y)
        West -> Coordinate(lastCoordinate.x - 1, lastCoordinate.y)
    }
}

object Task1 {
    fun housesThatReceiveAtLeastOnePresent(input: String): Int {
        return housesThatReceivePresent(input).toSet().size
    }
}

object Task2 {
    fun housesThatReceiveAtLeastOnePresent(input: String): Int {
        val santaInput = input.filterIndexed { index, _ -> index % 2 == 0 }
        val robotInput = input.filterIndexed { index, _ -> index % 2 == 1 }

        val allHouses = housesThatReceivePresent(santaInput) + housesThatReceivePresent(robotInput)
        return allHouses.toSet().size
    }
}