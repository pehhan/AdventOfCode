package adventofcode.year2015.day18

import adventofcode.year2015.day18.Light.Off
import adventofcode.year2015.day18.Light.On

enum class Light {
    On, Off
}

typealias State = List<List<Light>>

private fun toLights(line: String): List<Light> {
    return line.map { if (it == '#') On else Off }
}

object Task1 {
    fun numberOfLightsOn(input: String): Int {
        return numberOfLightsOn(input, false)
    }
}

object Task2 {
    fun numberOfLightsOn(input: String): Int {
        return numberOfLightsOn(input, true)
    }
}

private fun numberOfLightsOn(input: String, cornersAlwaysOn: Boolean): Int {
    var lights = input.lines().map { toLights(it) }

    repeat(100) {
        lights = update(lights, cornersAlwaysOn)
    }

    return lights.flatten().count { it == On }
}

private fun update(previousState: State, cornersAlwaysOn: Boolean): State {
    val newState = previousState.toMutableList().map { it.toMutableList() }

    for (x in previousState.indices) {
        val row = previousState[x]
        for (y in row.indices) {
            if (cornersAlwaysOn && isCorner(previousState, x, y)) continue

            val numberOfNeighboursOn = numberOfNeighboursOn(previousState, x, y)
            when (previousState[x][y]) {
                On -> newState[x][y] = if (numberOfNeighboursOn in 2..3) On else Off
                Off -> newState[x][y] = if (numberOfNeighboursOn == 3) On else Off
            }
        }
    }

    return newState
}

private fun isCorner(state: State, x: Int, y: Int): Boolean {
    return (x == 0 && y == 0) || (x == 0 && y == state[0].size - 1) || (x == state.size - 1 && y == 0) || (x == state.size - 1 && y == state[0].size - 1)
}

private fun numberOfNeighboursOn(state: State, x: Int, y: Int): Int {
    var ons = 0

    for (xx in (x - 1)..(x + 1)) {
        for (yy in (y - 1)..(y + 1)) {
            if (isOutOfBounds(state, xx, yy)) continue
            if (xx == x && yy == y) continue

            ons += if (state[xx][yy] == On) 1 else 0
        }
    }

    return ons
}

private fun isOutOfBounds(state: State, x: Int, y: Int): Boolean {
    return x < 0 || x >= state.size || y < 0 || y >= state[0].size
}