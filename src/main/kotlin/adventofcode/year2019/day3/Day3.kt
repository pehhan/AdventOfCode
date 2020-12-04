package adventofcode.year2019.day3

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

object Task1 {
    fun manhattanDistance(input: String): Int {
        val wires = input.lines()
        val intersections = Wire(wires[0]).findIntersectionsWith(Wire(wires[1]))
        return distanceToClosestIntersection(intersections) ?: -1
    }

    private fun distanceToClosestIntersection(intersections: List<Position>): Int? {
        return intersections.map { it.x + it.y }.filter { it > 0 }.minOrNull()
    }
}

object Task2 {
    fun fewestSteps(input: String): Int {
        val wires = input.lines()
        val steps = Wire(wires[0]).findStepsForIntersectionsWith(Wire(wires[1]))
        return steps.minOrNull() ?: -1
    }
}

data class Wire(val input: String) {

    private val segments = mutableListOf<Segment>()

    init {
        var lastPosition = Position(0, 0)

        val paths = input.split(",")
        for (path in paths) {
            val newPosition = newPositionFromPath(path, lastPosition)

            if (newPosition != null) {
                segments.add(Segment(lastPosition, newPosition))
                lastPosition = newPosition
            }
        }
    }

    private fun newPositionFromPath(path: String, lastPosition: Position): Position? {
        return when (path[0]) {
            'R' -> Position(lastPosition.x + valueForPath(path, 'R'), lastPosition.y)
            'L' -> Position(lastPosition.x - valueForPath(path, 'L'), lastPosition.y)
            'U' -> Position(lastPosition.x, lastPosition.y + valueForPath(path, 'U'))
            'D' -> Position(lastPosition.x, lastPosition.y - valueForPath(path, 'D'))
            else -> null
        }
    }

    private fun valueForPath(path: String, char: Char): Int {
        return path.substringAfter(char).toInt()
    }

    fun findIntersectionsWith(wire: Wire): List<Position> {
        val intersections = mutableListOf<Position>()
        for (lineA in segments) {
            for (lineB in wire.segments) {
                val intersection = lineA.findIntersectionWith(lineB)
                if (intersection != null) intersections.add(intersection)
            }
        }

        return intersections
    }

    fun findStepsForIntersectionsWith(wire: Wire): List<Int> {
        val steps = mutableListOf<Int>()

        var stepsA = 0
        for (lineA in segments) {
            stepsA += lineA.length()
            var stepsB = 0
            for (lineB in wire.segments) {
                stepsB += lineB.length()
                val intersection = lineA.findIntersectionWith(lineB)
                if (intersection != null) {
                    val distanceAToIntersection = lineA.distanceTo(intersection)
                    val distanceBToIntersection = lineB.distanceTo(intersection)

                    // Correct last step count
                    val resultA = stepsA - lineA.length() + distanceAToIntersection
                    val resultB = stepsB - lineB.length() + distanceBToIntersection

                    steps.add(resultA + resultB)
                }
            }
        }

        return steps
    }
}

data class Position(val x: Int, val y: Int)

data class Segment(val start: Position, val end: Position) {

    fun length(): Int {
        return distanceTo(end)
    }

    fun distanceTo(position: Position): Int {
        return when {
            start.x == end.x -> abs(max(start.y, position.y) - min(start.y, position.y))
            start.y == end.y -> abs(max(start.x, position.x) - min(start.x, position.x))
            else -> 0
        }
    }

    fun findIntersectionWith(segment: Segment): Position? {
        if (start.x == 0 && start.y == 0) return null
        if (segment.start.x == 0 && segment.start.y == 0) return null

        if (start.x != end.x && start.y == end.y) {
            if (segment.start.x == segment.end.x) {
                val minY = min(segment.start.y, segment.end.y)
                val maxY = max(segment.start.y, segment.end.y)

                val minX = min(start.x, end.x)
                val maxX = max(start.x, end.x)

                if (start.y in minY..maxY && segment.start.x in minX..maxX) {
                    return Position(segment.start.x, start.y)
                }
            }
        } else if (start.x == end.x && start.y != end.y) {
            if (segment.start.y == segment.end.y) {
                val minX = min(segment.start.x, segment.end.x)
                val maxX = max(segment.start.x, segment.end.x)

                val minY = min(start.y, end.y)
                val maxY = max(start.y, end.y)

                if (start.x in minX..maxX && segment.start.y in minY..maxY) {
                    return Position(start.x, segment.start.y)
                }
            }
        }

        return null
    }
}
