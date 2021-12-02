package adventofcode.year2021.day2

import adventofcode.year2021.day2.CommandType.*
import java.lang.IllegalArgumentException

enum class CommandType {
    Forward, Down, Up
}

data class Command(val type: CommandType, val value: Int) {

    companion object {
        fun fromLine(line: String): Command {
            val type = line.substringBefore(" ")
            val value = line.substringAfter(" ").toInt()

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
                Forward -> sub.copy(position = sub.position + command.value)
                Down -> sub.copy(depth = sub.depth + command.value)
                Up -> sub.copy(depth = sub.depth - command.value)
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
                Forward -> sub.copy(position = sub.position + command.value, depth = sub.depth + sub.aim * command.value)
                Down -> sub.copy(aim = sub.aim + command.value)
                Up -> sub.copy(aim = sub.aim - command.value)
            }
        }

        return sub.position * sub.depth
    }
}