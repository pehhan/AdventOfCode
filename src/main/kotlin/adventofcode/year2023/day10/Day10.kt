package adventofcode.year2023.day10

enum class Tile {
    VerticalPipe,
    HorizontalPipe,
    NorthEastPipe,
    NorthWestPipe,
    SouthEastPipe,
    SouthWestPipe,
    Ground,
    Start;

    fun connectsSouth(): Boolean {
        return this in setOf(VerticalPipe, SouthEastPipe, SouthWestPipe)
    }

    fun connectsNorth(): Boolean {
        return this in setOf(VerticalPipe, NorthEastPipe, NorthWestPipe)
    }

    fun connectsWest(): Boolean {
        return this in setOf(HorizontalPipe, NorthWestPipe, SouthWestPipe)
    }

    fun connectsEast(): Boolean {
        return this in setOf(HorizontalPipe, NorthEastPipe, SouthEastPipe)
    }
}

typealias Grid = Map<Position, Tile>

data class Position(val x: Int, val y: Int)

object Task1 {
    fun steps(input: String): Int {
        val grid = input.toGrid()
        val startPosition = grid.findStartPosition()
        val positionsAfterStart = grid.findConnectingPositionsForStart(startPosition)

        var steps = 1

        var previousPosition1 = startPosition
        var previousPosition2 = startPosition

        var position1 = positionsAfterStart.first()
        var position2 = positionsAfterStart.last()

        while (position1 != position2) {
            val tempPosition1 = position1
            val tempPosition2 = position2

            position1 = grid.findNextPosition(position1, previousPosition1)
            position2 = grid.findNextPosition(position2, previousPosition2)

            previousPosition1 = tempPosition1
            previousPosition2 = tempPosition2

            steps++
        }

        return steps
    }
}

private fun Grid.findStartPosition(): Position {
    return filterValues { it == Tile.Start }.keys.first()
}

private fun Grid.findNextPosition(currentPosition: Position, previousPosition: Position): Position {
    val tile = this[currentPosition] ?: throw IllegalArgumentException("No tile at position $currentPosition")

    return when (tile) {
        Tile.VerticalPipe ->  {
            if (currentPosition.y > previousPosition.y) {
                Position(currentPosition.x, currentPosition.y + 1)
            } else {
                Position(currentPosition.x, currentPosition.y - 1)
            }
        }
        Tile.HorizontalPipe -> {
            if (currentPosition.x > previousPosition.x) {
                Position(currentPosition.x + 1, currentPosition.y)
            } else {
                Position(currentPosition.x - 1, currentPosition.y)
            }
        }
        Tile.NorthEastPipe -> {
            if (currentPosition.y > previousPosition.y) {
                Position(currentPosition.x + 1, currentPosition.y)
            } else {
                Position(currentPosition.x, currentPosition.y - 1)
            }
        }
        Tile.NorthWestPipe -> {
            if (currentPosition.y > previousPosition.y) {
                Position(currentPosition.x - 1, currentPosition.y)
            } else {
                Position(currentPosition.x, currentPosition.y - 1)
            }
        }
        Tile.SouthEastPipe -> {
            if (currentPosition.x < previousPosition.x) {
                Position(currentPosition.x, currentPosition.y + 1)
            } else {
                Position(currentPosition.x + 1, currentPosition.y)
            }
        }
        Tile.SouthWestPipe -> {
            if (currentPosition.x > previousPosition.x) {
                Position(currentPosition.x, currentPosition.y + 1)
            } else {
                Position(currentPosition.x - 1, currentPosition.y)
            }
        }
        else -> {
            throw IllegalArgumentException("Unexpected tile $tile")
        }
    }
}

private fun Grid.findConnectingPositionsForStart(startPosition: Position): List<Position> {
    val north = Position(startPosition.x, startPosition.y - 1)
    val south = Position(startPosition.x, startPosition.y + 1)
    val west = Position(startPosition.x - 1, startPosition.y)
    val east = Position(startPosition.x + 1, startPosition.y)

    val result = mutableListOf<Position>()

    if (this[north]?.connectsSouth() == true) {
        result += north
    }

    if (this[south]?.connectsNorth() == true) {
        result += south
    }

    if (this[west]?.connectsEast() == true) {
        result += west
    }

    if (this[east]?.connectsWest() == true) {
        result += east
    }

    assert(result.size == 2)

    return result
}

private fun String.toGrid(): Grid {
    return lines()
        .mapIndexed { y, str -> str.mapIndexed { x, char -> Position(x, y) to char.toTile() } }
        .flatten()
        .toMap()
}

private fun Char.toTile(): Tile {
    return when (this) {
        '|' -> Tile.VerticalPipe
        '-' -> Tile.HorizontalPipe
        'L' -> Tile.NorthEastPipe
        'J' -> Tile.NorthWestPipe
        'F' -> Tile.SouthEastPipe
        '7' -> Tile.SouthWestPipe
        '.' -> Tile.Ground
        'S' -> Tile.Start
        else -> throw IllegalArgumentException("Unexpected tile: $this")
    }
}