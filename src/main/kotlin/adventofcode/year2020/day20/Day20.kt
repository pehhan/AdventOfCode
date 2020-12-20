package adventofcode.year2020.day20

import adventofcode.year2020.day20.Direction.*

enum class Direction {
    Up, Down, Left, Right
}

data class Side(val direction: Direction, val data: String)

data class Tile(val id: Long, val sides: List<Side>) {

    fun matches(tile: Tile): Boolean {
        for (side in sides) {
            for (otherSide in tile.sides) {
                if (side.data == otherSide.data) return true
            }
        }

        return false
    }

    companion object {
        fun from(input: String): Tile {
            val lines = input.lines()

            val id = lines.first().substring(lines.first().indexOf(" ") + 1, lines.first().indexOf(":")).toLong()

            val up = Side(Up, lines[1])
            val down = Side(Down, lines.last())
            val left = Side(Left, lines.drop(1).map { it[0] }.joinToString(""))
            val right = Side(Right, lines.drop(1).map { it[lines.last().length - 1] }.joinToString(""))

            val reversedUp = Side(Up, up.data.reversed())
            val reversedDown = Side(Down, down.data.reversed())
            val reversedLeft = Side(Left, left.data.reversed())
            val reversedRight = Side(Right, right.data.reversed())

            return Tile(id, listOf(up, down, left, right, reversedUp, reversedDown, reversedLeft, reversedRight))
        }
    }
}

object Task1 {
    fun result(input: String): Long {
        val tiles = input.split("\n\n").map { Tile.from(it) }

        return numberMatches(tiles)
            .filter { it.value == 2 }
            .keys
            .fold(1L) { product, tile -> product * tile.id }
    }

    private fun numberMatches(tiles: List<Tile>): Map<Tile, Int> {
        // The corners will have exactly two matches, all other tiles will have 3 or 4 matches.

        val matches = mutableMapOf<Tile, Int>()

        for (i in tiles.indices) {
            val tile1 = tiles[i]
            for (j in i + 1 until tiles.size) {
                val tile2 = tiles[j]
                if (tile1.matches(tile2)) {
                    val matchesTile1 = matches[tile1] ?: 0
                    val matchesTile2 = matches[tile2] ?: 0

                    matches[tile1] = matchesTile1 + 1
                    matches[tile2] = matchesTile2 + 1
                }
            }
        }

        return matches
    }
}