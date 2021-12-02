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

data class Sub(val position: Int, val depth: Int, val aim: Int)

object Task1 {
    fun getValue(lines: List<String>): Int {
        val commands = lines.map { Command.fromLine(it) }
        val sub = commands.fold(Sub(0, 0, 0)) { sub, command ->
            when (command.type) {
                Forward -> Sub(sub.position + command.value, sub.depth, sub.aim)
                Down -> Sub(sub.position, sub.depth + command.value, sub.aim)
                Up -> Sub(sub.position, sub.depth - command.value, sub.aim)
            }
        }

        return sub.position * sub.depth
    }
}

object Task2 {
    fun getValue(lines: List<String>): Int {
        val commands = lines.map { Command.fromLine(it) }
        val sub = commands.fold(Sub(0, 0, 0)) { sub, command ->
            when (command.type) {
                Forward -> Sub(sub.position + command.value, sub.depth + sub.aim * command.value, sub.aim)
                Down -> Sub(sub.position, sub.depth, sub.aim + command.value)
                Up -> Sub(sub.position, sub.depth, sub.aim - command.value)
            }
        }

        return sub.position * sub.depth
    }
}