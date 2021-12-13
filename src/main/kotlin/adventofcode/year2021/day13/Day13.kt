package adventofcode.year2021.day13

import adventofcode.year2021.day13.Orientation.Horizontal
import adventofcode.year2021.day13.Orientation.Vertical

enum class Orientation { Horizontal, Vertical }

data class Instruction(val orientation: Orientation, val coordinate: Int) {

    companion object {
        fun parse(line: String): Instruction {
            val orientation = if (line[line.indexOf('=') - 1] == 'y') Horizontal else Vertical
            val coordinate = line.substringAfter("=").toInt()

            return Instruction(orientation, coordinate)
        }
    }
}

data class Dot(val x: Int, val y: Int) {

    companion object {
        fun parse(line: String): Dot {
            val (x, y) = line.split(",").map { it.toInt() }
            return Dot(x, y)
        }
    }
}

data class Grid(private val dots: MutableSet<Dot>) {

    val size: Int
        get() = dots.size

    fun fold(instruction: Instruction) {
        when (instruction.orientation) {
            Horizontal -> {
                val dotsToFold = dots.filter { it.y > instruction.coordinate }.toSet()
                val newDots = dotsToFold.map { Dot(it.x, instruction.coordinate - (it.y - instruction.coordinate)) }

                dots -= dotsToFold
                dots += newDots
            }
            Vertical -> {
                val dotsToFold = dots.filter { it.x > instruction.coordinate }.toSet()
                val newDots = dotsToFold.map { Dot(instruction.coordinate - (it.x - instruction.coordinate), it.y) }

                dots -= dotsToFold
                dots += newDots

            }
        }
    }

    override fun toString(): String {
        val maxX = dots.maxOf { it.x }
        val maxY = dots.maxOf { it.y }

        var str = ""

        for (y in 0..maxY) {
            for (x in 0..maxX) {
                str += if (Dot(x, y) in dots) {
                    "#"
                } else {
                    "."
                }
            }
            str += "\n"
        }

        return str
    }
}

object Task1 {
    fun visibleDots(input: String): Int {
        val (instructions, grid) = parseInput(input)

        grid.fold(instructions.first())

        return grid.size
    }
}

object Task2 {
    fun code(input: String): String {
        val (instructions, grid) = parseInput(input)

        for (instruction in instructions) {
            grid.fold(instruction)
        }

        return grid.toString()
    }
}

private fun parseInput(input: String): Pair<List<Instruction>, Grid> {
    val (dots, rawInstructions) = input.split("\n\n")

    val instructions = rawInstructions.lines().map { Instruction.parse(it) }
    val grid = Grid(dots.lines().map { Dot.parse(it) }.toMutableSet())

    return instructions to grid
}