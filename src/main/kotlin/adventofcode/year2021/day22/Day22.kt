package adventofcode.year2021.day22

import adventofcode.year2021.day22.State.Off
import adventofcode.year2021.day22.State.On
import kotlin.math.max
import kotlin.math.min

enum class State { On, Off }

data class Position(val x: Int, val y: Int, val z: Int)

data class Step(val state: State, val xRange: IntRange, val yRange: IntRange, val zRange: IntRange) {

    fun onlyRangeWithin(start: Int, end: Int): Step {
        val decreasedXRange = IntRange(max(start, xRange.first), min(end, xRange.last))
        val decreasedYRange = IntRange(max(start, yRange.first), min(end, yRange.last))
        val decreasedZRange = IntRange(max(start, zRange.first), min(end, zRange.last))

        return Step(state, decreasedXRange, decreasedYRange, decreasedZRange)
    }

    companion object {
        fun parse(line: String): Step {
            val (rawState, rawRanges) = line.split(" ")
            val state = if (rawState == "on") On else Off
            val ranges = rawRanges.split(",")
            return Step(state, toRange(ranges[0]), toRange(ranges[1]), toRange(ranges[2]))
        }

        private fun toRange(input: String): IntRange {
            val start = input.substringAfter("=").substringBefore("..").toInt()
            val end = input.substringAfter("..").toInt()
            return IntRange(start, end)
        }
    }
}

object Task1 {
    fun numberOfCubesOn(input: String): Int {
        val steps = input
            .lines()
            .map { Step.parse(it) }
            .map { it.onlyRangeWithin(-50, 50) }

        return executeSteps(steps)
    }

    private fun executeSteps(steps: List<Step>): Int {
        val cubes = mutableMapOf<Position, State>()

        for (step in steps) {
            for (x in step.xRange) {
                for (y in step.yRange) {
                    for (z in step.zRange) {
                        cubes[Position(x, y, z)] = step.state
                    }
                }
            }
        }

        return cubes.count { it.value == On }
    }
}

object Task2 {
    fun numberOfCubesOn(input: String): Int {
        val steps = input
            .lines()
            .map { Step.parse(it) }

        return executeSteps(steps)
    }

    private fun executeSteps(steps: List<Step>): Int {
        val cubes = mutableMapOf<Position, State>()

        for (step in steps) {
            for (x in step.xRange) {
                for (y in step.yRange) {
                    for (z in step.zRange) {
                        cubes[Position(x, y, z)] = step.state
                    }
                }
            }
        }

        return cubes.count { it.value == On }
    }
}
