package adventofcode.year2021.day25

import adventofcode.year2021.day25.State.*
import java.lang.IllegalArgumentException

enum class State { MoveEast, MoveSouth, Empty }

typealias Grid = List<List<State>>

object Task1 {
    fun firstStepWithNoMoves(input: String): Int {
        var grid = parse(input)
        var newGrid = tick(grid)
        var i = 0

        while (grid != newGrid) {
            grid = newGrid.toMutableList()
            newGrid = tick(newGrid)
            i++
        }

        return i + 1
    }
}

private fun tick(grid: Grid): Grid {
    val grid1 = grid.toMutableList().map { it.toMutableList() }

    for (y in grid.indices) {
        for (x in grid[y].indices) {
            when (grid[y][x]) {
                MoveEast -> {
                    val newX = (x + 1) % grid[y].size
                    if (grid[y][newX] == Empty) {
                        grid1[y][newX] = MoveEast
                        grid1[y][x] = Empty
                    }
                }
                else -> {
                    // Do nothing
                }
            }
        }
    }

    val grid2 = grid1.toMutableList().map { it.toMutableList() }

    for (y in grid1.indices) {
        for (x in grid1[y].indices) {
            when (grid1[y][x]) {
                MoveSouth -> {
                    val newY = (y + 1) % grid1.size
                    if (grid1[newY][x] == Empty) {
                        grid2[newY][x] = MoveSouth
                        grid2[y][x] = Empty
                    }
                }
                else -> {
                    // Do nothing
                }
            }
        }
    }

    return grid2
}

private fun parse(input: String): Grid {
    val grid = mutableListOf<List<State>>()

    for (line in input.lines()) {
        val row = mutableListOf<State>()
        for (char in line) {
            row += parse(char)
        }
        grid += row
    }

    return grid
}

private fun parse(char: Char): State {
    return when (char) {
        'v' -> MoveSouth
        '>' -> MoveEast
        '.' -> Empty
        else -> throw IllegalArgumentException("Invalid char: $char")
    }
}
