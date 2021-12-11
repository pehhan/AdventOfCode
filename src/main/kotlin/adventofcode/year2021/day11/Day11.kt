package adventofcode.year2021.day11

data class Octopus(var energyLevel: Int, var hasFlashed: Boolean = false)

data class Grid(private val octopi: List<List<Octopus>>) {

    val size: Int
        get() = octopi.flatten().size

    var flashes = 0
    var totalFlashes = 0

    fun tick() {
        flashes = 0

        increaseEnergyLevel()
        flash()
        resetEnergyLevel()
    }

    private fun increaseEnergyLevel() {
        octopi.flatten().forEach { it.energyLevel++ }
    }

    private fun flash() {
        while (anyFlashersLeft()) {
            for (y in octopi.indices) {
                for (x in octopi[y].indices) {
                    val octopus = octopi[y][x]
                    if (octopus.energyLevel > 9 && !octopus.hasFlashed) {
                        flashNeighbors(x, y)

                        octopus.hasFlashed = true
                        flashes++
                        totalFlashes++
                    }
                }
            }
        }
    }

    private fun anyFlashersLeft(): Boolean {
        return octopi.flatten().any { it.energyLevel > 9 && !it.hasFlashed }
    }

    private fun flashNeighbors(x: Int, y: Int) {
        val dx = x - 1..x + 1
        val dy = y - 1..y + 1

        for (neighborY in dy) {
            for (neighborX in dx) {
                if (neighborX < 0 || neighborY < 0 || neighborX > octopi[0].size - 1 || neighborY > octopi.size - 1) continue

                octopi[neighborY][neighborX].energyLevel++
            }
        }
    }

    private fun resetEnergyLevel() {
        octopi
            .flatten()
            .filter { it.hasFlashed }
            .forEach {
                it.energyLevel = 0
                it.hasFlashed = false
            }
    }
}

object Task1 {
    fun totalFlashes(lines: List<String>, steps: Int): Int {
        val grid = toGrid(lines)

        repeat(steps) {
            grid.tick()
        }

        return grid.totalFlashes
    }
}

object Task2 {
    fun firstStepWhenAllFlash(lines: List<String>): Int {
        val grid = toGrid(lines)
        var step = 0

        while (grid.flashes != grid.size) {
            grid.tick()
            step++
        }

        return step
    }
}

private fun toGrid(lines: List<String>): Grid {
    return Grid(lines
        .map { it.toList() }
        .map { list -> list.map { Octopus(it.digitToInt()) } })
}