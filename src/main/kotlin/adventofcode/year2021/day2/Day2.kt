package adventofcode.year2021.day2

import adventofcode.year2021.day2.CommandType.*
import java.lang.IllegalArgumentException

enum class CommandType {
    Forward, Down, Up
}

data class Command(val type: CommandType, val value: Int) {

    companion object {
        fun fromLine(line: String): Command {
            val type = line.split(" ")[0]
            val value = line.split(" ")[1].toInt()

            return when (type) {
                "forward" -> Command(Forward, value)
                "down" -> Command(Down, value)
                "up" -> Command(Up, value)
                else -> throw IllegalArgumentException("Could not parse line: $line")
            }
        }
    }
}

data class Position(val horizontal: Int, val depth: Int, val aim: Int)

object Task1 {
    fun getValue(lines: List<String>): Int {
        val commands = lines.map { Command.fromLine(it)}
        val finalPosition = commands.fold(Position(0, 0, 0)) { position, command ->
            when (command.type) {
                Forward -> Position(position.horizontal + command.value, position.depth, position.aim)
                Down -> Position(position.horizontal, position.depth + command.value, position.aim)
                Up -> Position(position.horizontal, position.depth - command.value, position.aim)
            }
        }

        return finalPosition.horizontal * finalPosition.depth
    }
}

object Task2 {
    fun getValue(lines: List<String>): Int {
        val commands = lines.map { Command.fromLine(it) }
        val finalPosition = commands.fold(Position(0, 0, 0)) { position, command ->
            when (command.type) {
                Forward -> Position(position.horizontal + command.value, position.depth + position.aim * command.value, position.aim)
                Down -> Position(position.horizontal, position.depth, position.aim + command.value)
                Up -> Position(position.horizontal, position.depth, position.aim - command.value)
            }
        }

        return finalPosition.horizontal * finalPosition.depth
    }
}