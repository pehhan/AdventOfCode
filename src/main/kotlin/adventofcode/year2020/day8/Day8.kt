package adventofcode.year2020.day8

import adventofcode.year2020.day8.Operation.*
import adventofcode.year2020.day8.TerminationMode.InfiniteLoop
import adventofcode.year2020.day8.TerminationMode.Normal
import java.lang.IllegalArgumentException

enum class Operation {
    Acc, Jmp, Nop
}

data class Instruction(val operation: Operation, val argument: Int)

enum class TerminationMode {
    Normal, InfiniteLoop
}

data class Termination(val mode: TerminationMode, val accumulator: Int)

object Program {
    fun run(instructions: List<Instruction>): Termination {
        var i = 0
        var accumulator = 0
        val visited = mutableListOf<Int>()

        while (i < instructions.size) {
            if (visited.contains(i)) return Termination(InfiniteLoop, accumulator)

            visited += i

            val instruction = instructions[i]
            when (instruction.operation) {
                Acc -> {
                    accumulator += instruction.argument
                    i++
                }
                Jmp -> i += instruction.argument
                Nop -> i++
            }
        }

        return Termination(Normal, accumulator)
    }
}

private fun toInstruction(line: String): Instruction {
    val operation = when (line.split(" ")[0]) {
        "acc" -> Acc
        "jmp" -> Jmp
        "nop" -> Nop
        else -> throw IllegalArgumentException("Unknown instruction: $line")
    }

    val argument = line.split(" ")[1].toInt()

    return Instruction(operation, argument)
}

object Task1 {
    fun accumulatorBeforeInfiniteLoop(input: String): Int {
        val instructions = input.lines().map { toInstruction(it) }
        return Program.run(instructions).accumulator
    }
}

object Task2 {
    fun accumulatorForNormalTermination(input: String): Int {
        val instructions = input.lines().map { toInstruction(it) }

        for (i in instructions.indices) {
            val instruction = instructions[i]
            when (instruction.operation) {
                Jmp, Nop -> {
                    val newOperation = if (instruction.operation == Jmp) Nop else Jmp
                    val termination = runModifiedInstructions(instructions, i, Instruction(newOperation, instruction.argument))
                    if (termination.mode == Normal) return termination.accumulator
                }
            }
        }

        throw IllegalArgumentException("Could not find any instructions that cause normal termination.")
    }

    private fun runModifiedInstructions(instructions: List<Instruction>, index: Int, instruction: Instruction): Termination {
        val modifiedInstructions = instructions.toMutableList()
        modifiedInstructions[index] = instruction

        return Program.run(modifiedInstructions)
    }
}