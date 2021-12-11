package adventofcode.year2021.day11

data class Octopus(var energyLevel: Int, var hasFlashed: Boolean = false)

data class Grid(private val octopi: List<List<Octopus>>) {

    var totalFlashes = 0

    fun tick(): Grid {
        increaseEnergyLevel()
        flash()
        resetEnergyLevel()

        return this
    }

    private fun increaseEnergyLevel() {
        octopi
            .flatten()
            .forEach { it.energyLevel++ }
    }

    private fun flash() {
        while (anyFlashersLeft()) {
            for (y in octopi.indices) {
                for (x in octopi[y].indices) {
                    val octopus = octopi[x][y]
                    if (octopus.energyLevel > 9 && !octopus.hasFlashed) {
                        flashNeighbors(x, y)

                        octopus.hasFlashed = true
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

        for (neighborX in dx) {
            for (neighborY in dy) {
                if (neighborX < 0 || neighborY < 0 || neighborX > octopi[0].size - 1 || neighborY > octopi.size - 1) continue

                octopi[neighborX][neighborY].energyLevel++
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

    override fun toString(): String {
        var str = ""

        for (i in octopi.indices) {
            for (j in octopi[i].indices) {
                str += octopi[i][j].energyLevel
            }
            str += "\n"
        }

        return str
    }
}

object Task1 {
    fun totalFlashes(lines: List<String>, steps: Int): Int {
        val octopi = lines
            .map { it.toList() }
            .map { list -> list.map { Octopus(it.digitToInt()) } }

        var grid = Grid(octopi)

        repeat(steps) {
            grid = grid.tick()
            println("### Step ${it + 1}")
            println(grid)
        }

        println("#####")
        println(grid.totalFlashes)

        return 1
    }
}