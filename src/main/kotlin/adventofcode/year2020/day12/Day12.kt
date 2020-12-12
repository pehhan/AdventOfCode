package adventofcode.year2020.day12

import adventofcode.year2020.day12.Action.*
import java.lang.IllegalArgumentException
import kotlin.math.abs

enum class Action {
    North, South, East, West, TurnLeft, TurnRight, Forward
}

data class Instruction(val action: Action, val value: Int)

data class Position(val x: Int, val y: Int)

data class Ship(val position: Position, val rotation: Int)

data class Positions(val ship: Position, val waypoint: Position)

private fun toInstruction(str: String): Instruction {
    val value = str.drop(1).toInt()
    return when (str[0]) {
        'N' -> Instruction(North, value)
        'S' -> Instruction(South, value)
        'E' -> Instruction(East, value)
        'W' -> Instruction(West, value)
        'L' -> Instruction(TurnLeft, value)
        'R' -> Instruction(TurnRight, value)
        'F' -> Instruction(Forward, value)
        else -> throw IllegalArgumentException("Unknown instruction: $str")
    }
}

object Task1 {
    fun distance(input: String): Int {
        return input
            .lines()
            .map { toInstruction(it) }
            .fold(Ship(Position(0, 0), 0)) { ship, instruction ->
                when (instruction.action) {
                    North -> Ship(Position(ship.position.x, ship.position.y + instruction.value), ship.rotation)
                    South -> Ship(Position(ship.position.x, ship.position.y - instruction.value), ship.rotation)
                    East -> Ship(Position(ship.position.x + instruction.value, ship.position.y), ship.rotation)
                    West -> Ship(Position(ship.position.x - instruction.value, ship.position.y), ship.rotation)
                    TurnLeft -> Ship(Position(ship.position.x, ship.position.y), (ship.rotation + (360 - instruction.value)) % 360)
                    TurnRight -> Ship(Position(ship.position.x, ship.position.y), (ship.rotation + instruction.value) % 360)
                    Forward -> {
                        when (ship.rotation) {
                            0 -> Ship(Position(ship.position.x + instruction.value, ship.position.y), ship.rotation) // East
                            90 -> Ship(Position(ship.position.x, ship.position.y - instruction.value), ship.rotation) // South
                            180 -> Ship(Position(ship.position.x - instruction.value, ship.position.y), ship.rotation) // West
                            270 -> Ship(Position(ship.position.x, ship.position.y + instruction.value), ship.rotation) // North
                            else -> throw IllegalArgumentException("Unsupported rotation: ${ship.rotation}")
                        }
                    }
                }
            }
            .let { abs(it.position.x) + abs(it.position.y) }
    }
}

object Task2 {
    fun distance(input: String): Int {
        val ship = Position(0, 0)
        val waypoint = Position(10, 1)

        return input
            .lines()
            .map { toInstruction(it) }
            .fold(Positions(ship, waypoint)) { positions, instruction ->
                when (instruction.action) {
                    North -> Positions(positions.ship, Position(positions.waypoint.x, positions.waypoint.y + instruction.value))
                    South -> Positions(positions.ship, Position(positions.waypoint.x, positions.waypoint.y - instruction.value))
                    East -> Positions(positions.ship, Position(positions.waypoint.x + instruction.value, positions.waypoint.y))
                    West -> Positions(positions.ship, Position(positions.waypoint.x - instruction.value, positions.waypoint.y))
                    TurnLeft -> Positions(positions.ship, waypointForTurn(positions.waypoint, instruction))
                    TurnRight -> Positions(positions.ship, waypointForTurn(positions.waypoint, instruction))
                    Forward -> Positions(Position(positions.ship.x + (positions.waypoint.x * instruction.value), positions.ship.y + (positions.waypoint.y * instruction.value)), positions.waypoint)
                }
            }
            .let { abs(it.ship.x) + abs(it.ship.y) }
    }

    private fun waypointForTurn(position: Position, instruction: Instruction): Position {
        var x = position.x
        var y = position.y
        var tempX = x
        var remainingDegrees = instruction.value

        while (remainingDegrees > 0) {
            x = if (instruction.action == TurnLeft) -y else y
            y = if (instruction.action == TurnLeft) tempX else -tempX
            tempX = x
            remainingDegrees -= 90
        }

        return Position(x, y)
    }
}
