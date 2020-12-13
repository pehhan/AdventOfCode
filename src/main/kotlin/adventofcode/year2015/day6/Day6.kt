package adventofcode.year2015.day6

import adventofcode.year2015.day6.Action.*
import adventofcode.year2015.day6.Light.Off
import adventofcode.year2015.day6.Light.On
import java.lang.IllegalArgumentException
import kotlin.math.max

enum class Light {
    On, Off
}

enum class Action {
    TurnOn, TurnOff, Toggle
}

data class Instruction(val action: Action, val rangeX: IntRange, val rangeY: IntRange)

private fun toInstruction(input: String): Instruction {
    val action = when {
        input.contains("on") -> TurnOn
        input.contains("off") -> TurnOff
        input.contains("toggle") -> Toggle
        else -> throw IllegalArgumentException("Unknown instruction: $input")
    }

    val from = input.substringBefore(" through").split(" ").last().split(",")
    val to = input.substringAfter("through ").split(",")

    val rangeX = from[0].toInt()..to[0].toInt()
    val rangeY = from[1].toInt()..to[1].toInt()

    return Instruction(action, rangeX, rangeY)
}

object Task1 {
    fun numberOfTurnedOnLights(input: String): Int {
        val initialGrid = List(1000) { MutableList(1000) { Off } }

        return input
            .lines()
            .map { toInstruction(it) }
            .fold(initialGrid) { grid, instruction ->
                for (x in instruction.rangeX) {
                    for (y in instruction.rangeY) {
                        when (instruction.action) {
                            TurnOn -> grid[x][y] = On
                            TurnOff -> grid[x][y] = Off
                            Toggle -> grid[x][y] = if (initialGrid[x][y] == On) Off else On
                        }
                    }
                }

                grid
            }
            .flatten()
            .count { it == On }
    }
}

object Task2 {
    fun totalBrightness(input: String): Int {
        val initialGrid = List(1000) { MutableList(1000) { 0 } }

        return input
            .lines()
            .map { toInstruction(it) }
            .fold(initialGrid) { grid, instruction ->
                for (x in instruction.rangeX) {
                    for (y in instruction.rangeY) {
                        when (instruction.action) {
                            TurnOn -> grid[x][y] += 1
                            TurnOff -> grid[x][y] = max(0, grid[x][y] - 1)
                            Toggle -> grid[x][y] += 2
                        }
                    }
                }

                grid
            }
            .flatten()
            .sum()
    }
}