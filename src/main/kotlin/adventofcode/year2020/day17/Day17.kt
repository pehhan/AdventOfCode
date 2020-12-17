package adventofcode.year2020.day17

import adventofcode.year2020.day17.State.*

data class Cube(val x: Int, val y: Int, val z: Int, val w: Int)

enum class State {
    Active, Inactive
}

typealias Grid = Map<Cube, State>

data class PocketDimension(val grid: Grid, val dimensions: Int, val xRange: IntRange, val yRange: IntRange, val zRange: IntRange, val wRange: IntRange) {

    init {
        require(dimensions in 3..4)
    }

    fun nextIteration(): PocketDimension {
        val nextGrid = mutableMapOf<Cube, State>()

        val nextXRange = xRange.expand(1)
        val nextYRange = yRange.expand(1)
        val nextZRange = zRange.expand(1)
        val nextWRange = if (dimensions == 4) wRange.expand(1) else wRange

        for (x in nextXRange) {
            for (y in nextYRange) {
                for (z in nextZRange) {
                    for (w in nextWRange) {
                        val cube = Cube(x, y, z, w)
                        val state = grid[cube] ?: Inactive
                        val activeNeighbours = activeNeighbours(cube)
                        when (state) {
                            Active -> nextGrid[cube] = if (activeNeighbours in 2..3) Active else Inactive
                            Inactive -> nextGrid[cube] = if (activeNeighbours == 3) Active else Inactive
                        }
                    }
                }
            }
        }

        return PocketDimension(nextGrid, dimensions, nextXRange, nextYRange, nextZRange, nextWRange)
    }

    private fun activeNeighbours(cube: Cube): Int {
        var activeNeighbours = 0

        for (x in cube.x.expand(1)) {
            for (y in cube.y.expand(1)) {
                for (z in cube.z.expand(1)) {
                    for (w in cube.w.expand(1)) {
                        if (x == cube.x && y == cube.y && z == cube.z && w == cube.w) continue

                        activeNeighbours += if (grid[Cube(x, y, z, w)] == Active) 1 else 0
                    }
                }
            }
        }

        return activeNeighbours
    }

    private fun printGrid() {
        for (z in zRange) {
            println("z = $z")
            for (w in wRange) {
                println("w = $w")
                for (x in xRange) {
                    for (y in yRange) {
                        print(if (grid[Cube(x, y, z, w)] == Active) "#" else ".")
                    }
                    println()
                }
            }
            println()
        }
    }

    companion object {
        fun from(str: String, dimensions: Int): PocketDimension {
            val grid = mutableMapOf<Cube, State>()
            val rows = str.lines()
            for (x in rows.indices) {
                val row = rows[x]
                for (y in row.indices) {
                    grid[Cube(x, y, 0, 0)] = if (row[y] == '#') Active else Inactive
                }
            }

            return PocketDimension(grid, dimensions, rows.indices, rows[0].indices, 0..0, 0..0)
        }
    }
}

object Task1 {
    fun numberOfActiveCubes(input: String): Int {
        return numberOfActiveCubes(input, iterations = 6, dimensions = 3)
    }
}

object Task2 {
    fun numberOfActiveCubes(input: String): Int {
        return numberOfActiveCubes(input, iterations = 6, dimensions = 4)
    }
}

private fun numberOfActiveCubes(input: String, iterations: Int, dimensions: Int): Int {
    val dimension = PocketDimension.from(input, dimensions)
    val finalDimension = generateSequence(dimension) { it.nextIteration() }.take(iterations + 1).last()

    return finalDimension.grid.values.count { it == Active }
}

private fun IntRange.expand(n: Int): IntRange {
    return IntRange(first - n, last + n)
}

private fun Int.expand(n: Int): IntRange {
    return IntRange(this - n, this + n)
}