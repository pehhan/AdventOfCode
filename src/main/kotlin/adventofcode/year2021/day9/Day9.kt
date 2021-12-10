package adventofcode.year2021.day9

data class Point(val x: Int, val y: Int) {

    override fun toString(): String {
        return "($x, $y)"
    }
}

data class Grid(val grid: List<List<Int>>) {

    private fun findLowPoints(): List<Point> {
        return grid
            .flatMapIndexed { y, list -> list.mapIndexed { x, _ -> Point(x, y) } }
            .filter { isLowPoint(it) }
    }

    fun findLowPointValues(): List<Int> {
        return findLowPoints().map { grid[it.y][it.x] }
    }

    private fun isLowPoint(point: Point): Boolean {
        return getNeighbors(point).all { grid[it.y][it.x] > grid[point.y][point.x] }
    }

    private fun getNeighbors(point: Point): List<Point> {
        val x = point.x
        val y = point.y

        return when (x) {
            0 -> when (y) {
                0 -> listOf(Point(x, y + 1), Point(x + 1, y))
                grid.size - 1 -> listOf(Point(x, y - 1), Point(x + 1, y))
                else -> listOf(Point(x, y - 1), Point(x, y + 1), Point(x + 1, y))
            }
            grid[0].size - 1 -> when (y) {
                0 -> listOf(Point(x, y + 1), Point(x - 1, y))
                grid.size - 1 -> listOf(Point(x, y - 1), Point(x - 1, y))
                else -> listOf(Point(x, y - 1), Point(x, y + 1), Point(x - 1, y))
            }
            else -> when (y) {
                0 -> listOf(Point(x, y + 1), Point(x - 1, y), Point(x + 1, y))
                grid.size - 1 -> listOf(Point(x, y - 1), Point(x - 1, y), Point(x + 1, y))
                else -> listOf(Point(x, y - 1), Point(x, y + 1), Point(x - 1, y), Point(x + 1, y))
            }
        }
    }

    fun findValleys(): List<Set<Point>> {
        val lowPoints = findLowPoints()
        val valleys = mutableListOf<MutableSet<Point>>()

        println("lowpoints = $lowPoints")

        for (point in lowPoints) {
            val valley = mutableSetOf(point)

            val neighbors = getNeighbors(point).filter { grid[it.y][it.x] != 9 }.filter { !valleys.flatten().contains(it) }

            println("1. neighbors = $neighbors")

            for (neighbor in neighbors) {
                valley += findValley(neighbor, neighbors + valleys.flatten())
            }

            valleys += valley
        }

        return valleys
    }

    private fun findValley(point: Point, previousNeighbors: List<Point>): Set<Point> {
        if (grid[point.y][point.x] == 9) return emptySet()

        val neighbors = getNeighbors(point).filter { grid[it.y][it.x] != 9 }.filter { !previousNeighbors.contains(it) }

        val valley = mutableSetOf(point)

        for (neighbor in neighbors) {
            valley += findValley(neighbor, previousNeighbors + neighbors)
        }

        return valley
    }
}



object Task1 {
    fun riskLevelSum(lines: List<String>): Int {
        return Grid(lines.map { line -> line.toList().map { it.digitToInt() } })
            .findLowPointValues()
            .sumOf { it + 1 }
    }
}

object Task2 {
    fun largestBasinsProduct(lines: List<String>): Int {
        val basins = Grid(lines.map { line -> line.toList().map { it.digitToInt() } })
            .findValleys()
            .map { it.size }
            .sortedDescending()

        return basins[0] * basins[1] * basins[2]
    }
}
