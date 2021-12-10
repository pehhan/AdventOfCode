package adventofcode.year2021.day9

data class Point(val x: Int, val y: Int)

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

    fun findBasins(): List<Set<Point>> {
        val lowPoints = findLowPoints()
        val basins = mutableListOf<Set<Point>>()

        for (point in lowPoints) {
            var basin = setOf(point)
            var expandedBasin: Set<Point>

            while (true) {
                expandedBasin = expandBasin(basin)

                if (basin.size == expandedBasin.size) {
                    basins += basin
                    break
                }

                basin = expandedBasin
            }
        }

        return basins
    }

    private fun expandBasin(basin: Set<Point>): Set<Point> {
        val newBasin = basin.toMutableSet()
        for (point in basin) {
            val validNeighbors = getNeighbors(point).filter { grid[it.y][it.x] != 9 }
            newBasin.addAll(validNeighbors)
        }
        return newBasin
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
        return Grid(lines.map { line -> line.toList().map { it.digitToInt() } })
            .findBasins()
            .map { it.size }
            .sortedDescending()
            .take(3)
            .reduce { acc, i -> acc * i }
    }
}
