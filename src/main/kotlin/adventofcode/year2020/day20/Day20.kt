package adventofcode.year2020.day20

import java.lang.IllegalArgumentException

object Task1 {
    fun result(input: String): Long {
        val tiles = input.split("\n\n").map { Tile.fromLines(it.lines()) }
        return Image(tiles).corners().fold(1L) { product, tile -> product * tile.id }
    }
}

object Task2 {
    fun result(input: String): Int {
        val tiles = input.split("\n\n").map { Tile.fromLines(it.lines()) }
        val assembledTile = Tile.fromTiles(removeBorders(Image(tiles).assemble()))

        val patternToFind = listOf(
            "                  # ",
            "#    ##    ##    ###",
            " #  #  #  #  #  #   "
        )

        val patternMatches = assembledTile.findPatternInPermutations(patternToFind) ?: throw IllegalArgumentException("Could not find pattern in image")
        return patternMatches.tile.waterRoughness(patternToFind, patternMatches)
    }
}

data class Tile(val id: Long = 0, val rows: List<String>) {

    fun permutations(): List<Tile> {
        return listOf(
            this,
            rotate(),
            rotate().rotate(),
            rotate().rotate().rotate(),
            flipHorizontal(),
            flipHorizontal().rotate(),
            flipHorizontal().rotate().rotate(),
            flipHorizontal().rotate().rotate().rotate(),
            flipVertical(),
            flipVertical().rotate(),
            flipVertical().rotate().rotate(),
            flipVertical().rotate().rotate().rotate()
        )
    }

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
        return listOf(rows.first(), rows.map { it.last() }.join(), rows.last().reversed(), rows.map { it.first() }.join().reversed())
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

    fun withoutBorder(): Tile {
        val newRows = rows.drop(1).dropLast(1).map { it.substring(1, it.length - 1) }
        return Tile(id, newRows)
    }

    fun findPatternInPermutations(pattern: List<String>): PatternMatch? {
        for (permutation in permutations()) {
            val match = findPatternInPermutation(pattern, permutation)
            if (match != null) {
                return match
            }
        }

        return null
    }

    // # means it has to match #, anything else means it can be anything
    private fun findPatternInPermutation(pattern: List<String>, permutation: Tile): PatternMatch? {
        var matches = 0

        val rows = permutation.rows
        for (i in rows.indices) {
            if (i + pattern.size > rows.size) {
                // Not possible to find pattern this far down in the rows
                break
            }

            var row = rows[i]
            val rowLength = row.length
            for (j in row.indices) {
                if (j + pattern[0].length > rowLength) {
                    // Can't find pattern this far right on the row
                    break
                }

                var match = true
                for (k in pattern.indices) {
                    row = rows[i + k]
                    for (l in pattern[k].indices) {
                        val patternChar = pattern[k][l]
                        if (patternChar == '#') {
                            if (row[j + l] != '#') {
                                match = false
                            }
                        }
                    }
                }

                if (match) {
                    matches++
                }
            }
        }

        return if (matches == 0) {
            null
        } else {
            PatternMatch(permutation, matches)
        }
    }

    fun waterRoughness(pattern: List<String>, patternMatches: PatternMatch): Int {
        val total = rows.joinToString("").count { it == '#' }
        val seaMonsters = patternMatches.numberOfMatches * pattern.joinToString("").count { it == '#' }

        return total - seaMonsters
    }

    override fun toString(): String {
        var result = "Tile $id:\n"
        result += rows.joinToString("\n")
        return result
    }

    companion object {
        fun fromLines(lines: List<String>): Tile {
            val id = lines.first().substring(lines.first().indexOf(" ") + 1, lines.first().indexOf(":")).toLong()
            val rows = lines.drop(1)
            return Tile(id, rows)
        }

        fun fromTiles(tiles: List<List<Tile>>): Tile {
            val result = mutableListOf<String>()
            val rows = tiles.map { tile -> tile.map { it.rows } }

            for (row in rows) {
                for (i in row[0].indices) {
                    var str = ""
                    for (element in row) {
                        str += element[i]
                    }
                    result += str
                }
            }

            return Tile(0L, result)
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

    fun assemble(): List<List<Tile>> {
        // Assemble the tiles in the correct order, starting with a corner
        val remainingTiles = tiles.toMutableList()

        val corners = corners()
        assert(corners.size == 4)

        // Since we start with the first corner, we should remove it from the list of remaining
        remainingTiles -= corners[0]

        // Find the two tiles that match the corner tile.
        val matches = findMatches(corners[0], remainingTiles)
        assert(matches.size == 2)

        // Make the corner we start with the top left corner of the image
        val start = findOrientationForTopLeftCorner(corners[0], matches)

        var currentTile = start
        var firstTileInPreviousRow = start
        var currentRow = 0

        val assembledTiles = mutableListOf<MutableList<Tile>>()
        assembledTiles += mutableListOf<Tile>()
        assembledTiles[currentRow].add(start)

        // Go column by column and find the match on the right side (edge 1) of the tile.
        // If there is no match on the right side, that means we have reached the end
        // of the current column and can jump to the next row.
        while (remainingTiles.isNotEmpty()) {
            val nextTileToTheRight = findEdgeMatch(currentTile.edges()[1], 3, remainingTiles)
            if (nextTileToTheRight != null) {
                assembledTiles[currentRow].add(nextTileToTheRight)
                currentTile = nextTileToTheRight

                remainingTiles.remove(remainingTiles.first { it.id == nextTileToTheRight.id })
            } else {
                // We have moved on to the next row.
                val nextTileToTheBottom = findEdgeMatch(firstTileInPreviousRow.edges()[2], 0, remainingTiles)
                if (nextTileToTheBottom != null) {
                    assembledTiles += mutableListOf<Tile>()
                    currentRow++

                    assembledTiles[currentRow].add(nextTileToTheBottom)
                    firstTileInPreviousRow = nextTileToTheBottom
                    currentTile = nextTileToTheBottom

                    remainingTiles.remove(remainingTiles.first { it.id == nextTileToTheBottom.id })
                } else {
                    // We have reached the end
                    break
                }
            }
        }

        return assembledTiles
    }

    // This will only work if there is not more than 1 match for each side...
    private fun findEdgeMatch(edge: String, matchingEdgeIndex: Int, tiles: List<Tile>): Tile? {
        for (tile in tiles) {
            for (permutation in tile.permutations()) {
                val edges = permutation.edges()
                if (edge == edges[matchingEdgeIndex].reversed()) {
                    return permutation
                }
            }
        }

        return null
    }

    private fun findMatches(tile: Tile, tilesToMatch: List<Tile>): List<Match> {
        val matches = mutableListOf<Match>()

        val firstEdges = tile.edges()

        outer@ for (tileToMatch in tilesToMatch) {
            val permutations = listOf(tileToMatch, tileToMatch.flipHorizontal(), tileToMatch.flipVertical())
            for (permutation in permutations) {
                val secondEdges = permutation.edges()
                for (firstEdge in firstEdges) {
                    for (secondEdge in secondEdges) {
                        if (firstEdge == secondEdge) {
                            val match = Match(tile, permutation, firstEdge)
                            matches += match
                            continue@outer
                        }
                    }
                }
            }
        }

        return matches
    }

    private fun findOrientationForTopLeftCorner(tile: Tile, matches: List<Match>): Tile {
        for (permutation in tile.permutations()) {
            if (isMatchingTopLeftCorner(permutation.edges(), matches)) {
                return permutation
            }
        }

        throw IllegalArgumentException("Could not find correct orientation for corner tile")
    }

    private fun isMatchingTopLeftCorner(startEdges: List<String>, matches: List<Match>): Boolean {
        return (matches[0].edge.reversed() == startEdges[1] || matches[0].edge.reversed() == startEdges[2]) && (matches[1].edge.reversed() == startEdges[1] || matches[1].edge.reversed() == startEdges[2])
    }
}

data class Match(val tile1: Tile, val tile2: Tile, val edge: String)

data class PatternMatch(val tile: Tile, val numberOfMatches: Int)

private fun removeBorders(tiles: List<List<Tile>>): List<List<Tile>> {
    return tiles.map { row -> row.map { it.withoutBorder() } }
}

private fun List<Char>.join(): String {
    return joinToString("")
}