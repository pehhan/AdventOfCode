package adventofcode.year2021.day17

import kotlin.math.abs
import kotlin.math.max

data class Probe(private var x: Int, private var y: Int, private var xVelocity: Int, private var yVelocity: Int) {

    var maxY = Int.MIN_VALUE

    fun tick() {
        x += xVelocity
        y += yVelocity

        maxY = max(y, maxY)

        if (xVelocity != 0) {
            if (x > 0) {
                xVelocity--
            } else if (x < 0) {
                xVelocity++
            }
        }

        yVelocity--
    }

    fun isInRange(xRange: IntRange, yRange: IntRange): Boolean {
        return x in xRange && y in yRange
    }

    fun hasOvershot(xRange: IntRange, yRange: IntRange): Boolean {
        return x > xRange.last || y < yRange.first
    }

    fun reset(xVelocity: Int, yVelocity: Int) {
        x = 0
        y = 0
        this.xVelocity = xVelocity
        this.yVelocity = yVelocity
        maxY = Int.MIN_VALUE
    }
}

data class ProbeData(val maxY: Int, val matches: Int)

object Task1 {
    fun maxY(input: String): Int {
        return getProbeData(input).maxY
    }
}

object Task2 {
    fun numberOfVelocities(input: String): Int {
        return getProbeData(input).matches
    }
}

private fun getProbeData(input: String): ProbeData {
    val (xRange, yRange) = parseTargetRange(input)

    var xVelocity = 1
    var yVelocity = 0
    val probe = Probe(0, 0, xVelocity, yVelocity)
    var maxY = Int.MIN_VALUE
    var matches = 0
    val maxYVelocity = max(abs(yRange.first), abs(yRange.last))

    while (true) {
        while (!probe.isInRange(xRange, yRange) && !probe.hasOvershot(xRange, yRange)) {
            probe.tick()
        }

        if (probe.isInRange(xRange, yRange)) {
            maxY = max(probe.maxY, maxY)
            matches++
        }

        xVelocity++

        if (xVelocity > xRange.last) {
            if (yVelocity >= 0) {
                yVelocity++
            } else {
                yVelocity--
            }

            xVelocity = 0
        }

        if (yVelocity > maxYVelocity) {
            yVelocity = -1
        }

        probe.reset(xVelocity, yVelocity)

        if (yVelocity < 0 && abs(yVelocity) > maxYVelocity) break
    }

    return ProbeData(maxY, matches)
}

private fun parseTargetRange(input: String): Pair<IntRange, IntRange> {
    val (xRange, yRange) = input.substringAfter(": ").split(", ")

    val (xFrom, xTo) = xRange.substringAfter("=").split("..").map { it.toInt() }
    val (yFrom, yTo) = yRange.substringAfter("=").split("..").map { it.toInt() }

    return xFrom..xTo to yFrom..yTo
}