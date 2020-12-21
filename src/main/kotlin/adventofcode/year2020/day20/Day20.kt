package adventofcode.year2020.day20

object Task1 {
    fun result(input: String): Long {
        val tiles = input.split("\n\n").map { Tile.from(it.lines()) }
        return Image(tiles).corners().fold(1L) { product, tile -> product * tile.id }
    }
}

data class Tile(val id: Long, val rows: List<String>) {

    fun rotate(): Tile {
        // Rotates 90 degrees clockwise
        return Tile(id, rows.mapIndexed { index, _ -> rows.map { it[index] }.join().reversed() })
    }

    fun flipHorizontal(): Tile {
        return Tile(id, rows.map { it.reversed() })
    }

    fun flipVertical(): Tile {
        return Tile(id, rows.reversed())
    }

    fun edges(): List<String> {
        return listOf(rows.first(), rows.map { it.last() }.join(), rows.last(), rows.map { it.first() }.join())
    }

    fun edgeMatches(other: Tile): Boolean {
        val permutations = listOf(this, flipHorizontal(), flipVertical())
        val otherEdges = other.edges()
        return permutations.any { edgesMatches(it.edges(), otherEdges) }
    }

    private fun edgesMatches(firstEdges: List<String>, secondEdges: List<String>): Boolean {
        for (firstEdge in firstEdges) {
            for (secondEdge in secondEdges) {
                if (firstEdge == secondEdge) return true
            }
        }

        return false
    }

    companion object {
        fun from(lines: List<String>): Tile {
            val id = lines.first().substring(lines.first().indexOf(" ") + 1, lines.first().indexOf(":")).toLong()
            val rows = lines.drop(1)
            return Tile(id, rows)
        }
    }
}

data class Image(val tiles: List<Tile>) {

    fun corners(): List<Tile> {
        // Assume that each corner only matches two other edges.
        val matches = mutableMapOf<Tile, Int>()

        for (i in tiles.indices) {
            val tile1 = tiles[i]
            for (j in i + 1 until tiles.size) {
                val tile2 = tiles[j]
                if (tile1.edgeMatches(tile2)) {
                    val matchesTile1 = matches[tile1] ?: 0
                    val matchesTile2 = matches[tile2] ?: 0

                    matches[tile1] = matchesTile1 + 1
                    matches[tile2] = matchesTile2 + 1
                }
            }
        }

        return matches.filter { it.value == 2 }.keys.toList()
    }
}

private fun List<Char>.join(): String {
    return joinToString("")
}