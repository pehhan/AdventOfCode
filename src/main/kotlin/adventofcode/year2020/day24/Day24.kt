package adventofcode.year2020.day24

import adventofcode.year2020.day24.Color.Black
import adventofcode.year2020.day24.Color.White
import adventofcode.year2020.day24.Direction.*

enum class Color {
    Black, White
}

enum class Direction {
    East, West, SouthEast, SouthWest, NorthEast, NorthWest
}

data class Position(val x: Int, val y: Int, val z: Int)

typealias Directions = List<Direction>
typealias Tiles = Map<Position, Color>

object Task1 {
    fun numberOfBlackTiles(input: String): Int {
        val directions = input.lines().map { toDirections(it) }
        return flipTiles(directions).values.count { it == Black }
    }

    fun flipTiles(allDirections: List<Directions>): Tiles {
        val tiles = mutableMapOf<Position, Color>()

        for (directions in allDirections) {
            val position = getPositionForDirections(directions)
            val color = tiles[position] ?: White
            tiles += position to if (color == White) Black else White
        }

        return tiles
    }
}

object Task2 {
    fun numberOfBlackTiles(input: String): Int {
        val directions = input.lines().map { toDirections(it) }
        var tiles = Task1.flipTiles(directions)

        repeat(100) {
            tiles = flipTiles(tiles)
        }

        return tiles.values.count { it == Black }
    }

    private fun flipTiles(previousTiles: Tiles): Tiles {
        val tiles = mutableMapOf<Position, Color>()

        for (tile in previousTiles) {
            val position = tile.key
            val color = previousTiles[position] ?: White
            val neighbours = getNeighbours(position, previousTiles)

            tiles += flipTile(position, color, neighbours)

            for (neighbour in neighbours) {
                tiles += flipTile(neighbour.key, neighbour.value, getNeighbours(neighbour.key, previousTiles))
            }
        }

        return tiles
    }

    private fun flipTile(position: Position, color: Color, neighbours: Tiles): Pair<Position, Color> {
        val numberOfBlackNeighbours = neighbours.values.count { it == Black }

        return when (color) {
            Black -> {
                if (numberOfBlackNeighbours == 0 || numberOfBlackNeighbours > 2) {
                    position to White
                } else {
                    position to Black
                }
            }
            White -> {
                if (numberOfBlackNeighbours == 2) {
                    position to Black
                } else {
                    position to White
                }
            }
        }
    }

    private fun getNeighbours(position: Position, tiles: Tiles): Tiles {
        val neighbours = mutableMapOf<Position, Color>()

        val east = Position(position.x + 1, position.y - 1, position.z)
        neighbours += east to (tiles[east] ?: White)

        val west = Position(position.x - 1, position.y + 1, position.z)
        neighbours += west to (tiles[west] ?: White)

        val southEast = Position(position.x, position.y - 1, position.z + 1)
        neighbours += southEast to (tiles[southEast] ?: White)

        val northWest = Position(position.x, position.y + 1, position.z - 1)
        neighbours += northWest to (tiles[northWest] ?: White)

        val southWest = Position(position.x - 1, position.y, position.z + 1)
        neighbours += southWest to (tiles[southWest] ?: White)

        val northEast = Position(position.x + 1, position.y, position.z - 1)
        neighbours += northEast to (tiles[northEast] ?: White)

        return neighbours
    }
}

private fun toDirections(line: String): Directions {
    val directions = mutableListOf<Direction>()
    var i = 0

    while (i < line.length) {
        when (line[i]) {
            's' -> {
                when (line[i + 1]) {
                    'e' -> directions += SouthEast
                    'w' -> directions += SouthWest
                }
                i += 2
            }
            'n' -> {
                when (line[i + 1]) {
                    'e' -> directions += NorthEast
                    'w' -> directions += NorthWest
                }
                i += 2
            }
            'e' -> {
                directions += East
                i++
            }
            'w' -> {
                directions += West
                i++
            }
        }
    }

    return directions
}

private fun getPositionForDirections(directions: Directions): Position {
    var x = 0
    var y = 0
    var z = 0

    for (direction in directions) {
        when (direction) {
            East -> {
                x++
                y--
            }
            West -> {
                x--
                y++
            }
            SouthEast -> {
                y--
                z++
            }
            SouthWest -> {
                x--
                z++
            }
            NorthEast -> {
                x++
                z--
            }
            NorthWest -> {
                y++
                z--
            }
        }
    }

    return Position(x, y, z)
}
