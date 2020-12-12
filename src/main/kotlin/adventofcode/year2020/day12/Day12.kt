package adventofcode.year2020.day12

import adventofcode.year2020.day12.Action.*
import java.lang.IllegalArgumentException
import kotlin.math.abs

enum class Action {
    MoveNorth, MoveSouth, MoveEast, MoveWest, TurnLeft, TurnRight, MoveForward
}

data class Instruction(val action: Action, val value: Int)

data class Position(val x: Int, val y: Int)

data class Ship(val position: Position, val rotation: Int)

data class Positions(val ship: Position, val waypoint: Position)

private fun toInstruction(str: String): Instruction {
    val value = str.drop(1).toInt()
    return when (str[0]) {
        'N' -> Instruction(MoveNorth, value)
        'S' -> Instruction(MoveSouth, value)
        'E' -> Instruction(MoveEast, value)
        'W' -> Instruction(MoveWest, value)
        'L' -> Instruction(TurnLeft, value)
        'R' -> Instruction(TurnRight, value)
        'F' -> Instruction(MoveForward, value)
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
                    MoveNorth -> Ship(Position(ship.position.x, ship.position.y + instruction.value), ship.rotation)
                    MoveSouth -> Ship(Position(ship.position.x, ship.position.y - instruction.value), ship.rotation)
                    MoveEast -> Ship(Position(ship.position.x + instruction.value, ship.position.y), ship.rotation)
                    MoveWest -> Ship(Position(ship.position.x - instruction.value, ship.position.y), ship.rotation)
                    TurnLeft -> Ship(Position(ship.position.x, ship.position.y), (ship.rotation + (360 - instruction.value)) % 360)
                    TurnRight -> Ship(Position(ship.position.x, ship.position.y), (ship.rotation + instruction.value) % 360)
                    MoveForward -> {
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
                    MoveNorth -> Positions(positions.ship, Position(positions.waypoint.x, positions.waypoint.y + instruction.value))
                    MoveSouth -> Positions(positions.ship, Position(positions.waypoint.x, positions.waypoint.y - instruction.value))
                    MoveEast -> Positions(positions.ship, Position(positions.waypoint.x + instruction.value, positions.waypoint.y))
                    MoveWest -> Positions(positions.ship, Position(positions.waypoint.x - instruction.value, positions.waypoint.y))
                    TurnLeft, TurnRight -> Positions(positions.ship, waypointForTurn(positions.waypoint, instruction))
                    MoveForward -> Positions(Position(positions.ship.x + (positions.waypoint.x * instruction.value), positions.ship.y + (positions.waypoint.y * instruction.value)), positions.waypoint)
                }
            }
            .let { abs(it.ship.x) + abs(it.ship.y) }
    }

    private fun waypointForTurn(position: Position, instruction: Instruction): Position {
        var x = position.x
        var y = position.y
        var tempX = x
        var degrees = instruction.value

        while (degrees > 0) {
            x = if (instruction.action == TurnLeft) -y else y
            y = if (instruction.action == TurnLeft) tempX else -tempX
            tempX = x
            degrees -= 90
        }

        return Position(x, y)
    }
}