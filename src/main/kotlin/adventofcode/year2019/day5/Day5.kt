package adventofcode.year2019.day5

import adventofcode.year2019.day5.Operation.*
import adventofcode.year2019.day5.ParameterMode.Immediate
import adventofcode.year2019.day5.ParameterMode.Position
import java.lang.IllegalArgumentException

fun runProgram(data: List<Int>, input: Int) {
    Program(data).run(input)
}

typealias OpCode = Int
typealias Parameter = Int

enum class ParameterMode {
    Position, Immediate
}

sealed class Operation(val steps: Int) {
    data class Add(val value1: Parameter, val value2: Parameter, val address: Parameter) : Operation(4)
    data class Multiply(val value1: Parameter, val value2: Parameter, val address: Parameter) : Operation(4)
    data class Input(val address: Parameter) : Operation(2)
    data class Output(val address: Parameter) : Operation(2)
    data class JumpIfTrue(val value: Parameter, val address: Parameter): Operation(3)
    data class JumpIfFalse(val value: Parameter, val address: Parameter): Operation(3)
    data class LessThan(val value1: Parameter, val value2: Parameter, val address: Parameter): Operation(4)
    data class Equals(val value1: Parameter, val value2: Parameter, val address: Parameter): Operation(4)
    object Terminate : Operation(0)
}

data class Program(val data: List<Int>) {

    fun run(input: Int) {
        val program = data.toMutableList()

        var i = 0
        while (i < data.size) {
            when (val operation = parseOperation(program, i)) {
                is Add -> {
                    program[operation.address] = program[operation.value1] + program[operation.value2]
                    i += operation.steps
                }
                is Multiply -> {
                    program[operation.address] = program[operation.value1] * program[operation.value2]
                    i += operation.steps
                }
                is Input -> {
                    program[operation.address] = input
                    i += operation.steps
                }
                is Output -> {
                    println(program[operation.address])
                    i += operation.steps
                }
                is JumpIfTrue -> {
                    if (program[operation.value] != 0) {
                        i = program[operation.address]
                    } else {
                        i += operation.steps
                    }
                }
                is JumpIfFalse -> {
                    if (program[operation.value] == 0) {
                        i = program[operation.address]
                    } else {
                        i += operation.steps
                    }
                }
                is LessThan -> {
                    program[operation.address] = if (program[operation.value1] < program[operation.value2]) 1 else 0
                    i += operation.steps
                }
                is Equals -> {
                    program[operation.address] = if (program[operation.value1] == program[operation.value2]) 1 else 0
                    i += operation.steps
                }
                Terminate -> break
            }
        }
    }

    private fun parseOperation(program: List<Int>, index: Int): Operation {
        return when (program[index]) {
            1 -> Add(program[index + 1], program[index + 2], program[index + 3])
            2 -> Multiply(program[index + 1], program[index + 2], program[index + 3])
            3 -> Input(program[index + 1])
            4 -> Output(program[index + 1])
            5 -> JumpIfTrue(program[index + 1], program[index + 2])
            6 -> JumpIfFalse(program[index + 1], program[index + 2])
            7 -> LessThan(program[index + 1], program[index + 2], program[index + 3])
            8 -> Equals(program[index + 1], program[index + 2], program[index + 3])
            99 -> Terminate
            else -> parseOperationWithParameters(program, index)
        }
    }

    private fun parseOperationWithParameters(program: List<Int>, index: Int): Operation {
        val opCode = program[index]
        return when (val parsedOpCode = opCode.digitAtPositionFromEnd(0)) {
            1, 2 -> {
                val value1 = parameter(program, opCode, index + 1, 2)
                val value2 = parameter(program, opCode, index + 2, 3)
                val address = parameter(program, opCode, index + 3, 4)

                if (parsedOpCode == 1) Add(value1, value2, address) else Multiply(value1, value2, address)
            }
            3, 4 -> {
                val address = parameter(program, opCode, index + 1, 2)

                if (parsedOpCode == 3) Input(address) else Output(address)
            }
            5, 6 -> {
                val value = parameter(program, opCode, index + 1, 2)
                val address = parameter(program, opCode, index + 2, 3)

                if (parsedOpCode == 5) JumpIfTrue(value, address) else JumpIfFalse(value, address)
            }
            7, 8 -> {
                val value1 = parameter(program, opCode, index + 1, 2)
                val value2 = parameter(program, opCode, index + 2, 3)
                val address = parameter(program, opCode, index + 3, 4)

                if (parsedOpCode == 7) LessThan(value1, value2, address) else Equals(value1, value2, address)
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
    if (position >= toString().length) return Position // Counts as 0 in this case which means Position.
    return if (digitAtPositionFromEnd(position) == 0) Position else Immediate
}