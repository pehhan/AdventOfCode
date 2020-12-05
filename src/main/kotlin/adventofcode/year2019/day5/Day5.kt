package adventofcode.year2019.day5

import adventofcode.year2019.day5.Operation.*
import adventofcode.year2019.day5.ParameterMode.Immediate
import adventofcode.year2019.day5.ParameterMode.Position
import java.lang.IllegalArgumentException

object Task1 {
    fun runProgram(data: List<Int>, input: Int) {
        IntCodeProgram(data).run(input)
    }
}

typealias OpCode = Int
typealias Parameter = Int

enum class ParameterMode {
    Position, Immediate
}

sealed class Operation(val steps: Int) {
    data class AddOperation(val operator1: Parameter, val operator2: Parameter, val address: Parameter) : Operation(4)
    data class MultiplyOperation(val operator1: Parameter, val operator2: Parameter, val address: Parameter) : Operation(4)
    data class InputOperation(val address: Parameter) : Operation(2)
    data class OutputOperation(val address: Parameter) : Operation(2)
    object TerminateOperation : Operation(0)
}

data class IntCodeProgram(val data: List<Int>) {

    fun run(input: Int) {
        val program = data.toMutableList()

        var i = 0
        while (i < data.size) {
            val operation = parseOperation(program, i)
            when (operation) {
                is AddOperation -> program[operation.address] = program[operation.operator1] + program[operation.operator2]
                is MultiplyOperation -> program[operation.address] = program[operation.operator1] * program[operation.operator2]
                is InputOperation -> program[operation.address] = input
                is OutputOperation -> println(program[operation.address])
                TerminateOperation -> break
            }

            i += operation.steps
        }
    }

    private fun parseOperation(program: List<Int>, index: Int): Operation {
        return when (program[index]) {
            1 -> AddOperation(program[index + 1], program[index + 2], program[index + 3])
            2 -> MultiplyOperation(program[index + 1], program[index + 2], program[index + 3])
            3 -> InputOperation(program[index + 1])
            4 -> OutputOperation(program[index + 1])
            99 -> TerminateOperation
            else -> parseOperationWithParameters(program, index)
        }
    }

    private fun parseOperationWithParameters(program: List<Int>, index: Int): Operation {
        val opCode = program[index]
        return when (val parsedOpCode = opCode.digitAtPositionFromEnd(0)) {
            1, 2 -> {
                val operator1 = parameter(program, opCode, index + 1, 2)
                val operator2 = parameter(program, opCode, index + 2, 3)
                val address = parameter(program, opCode, index + 3, 4)

                if (parsedOpCode == 1) AddOperation(operator1, operator2, address) else MultiplyOperation(operator1, operator2, address)
            }
            3, 4 -> {
                val address = parameter(program, opCode, index + 1, 2)

                if (parsedOpCode == 3) InputOperation(address) else OutputOperation(address)
            }
            else -> throw IllegalArgumentException("Unexpected op code: $opCode")
        }
    }

    private fun parameter(program: List<Int>, opCode: OpCode, index: Int, positionFromEnd: Int): Parameter {
        return if (opCode.parameterMode(positionFromEnd) == Position) program[index] else index
    }
}

fun OpCode.digitAtPositionFromEnd(position: Int): Int {
    val str = toString()
    return str.substring(str.length - position - 1, str.length - position).toInt()
}

fun OpCode.parameterMode(position: Int): ParameterMode {
    if (position >= toString().length) return Position
    return if (digitAtPositionFromEnd(position) == 0) Position else Immediate
}