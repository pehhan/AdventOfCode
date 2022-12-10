package adventofcode.year2022.day10

sealed class Instruction(val cycles: Int)

object Noop : Instruction(1)
data class AddX(val value: Int) : Instruction(2)

data class Execution(val instruction: AddX, val atCycle: Int)

data class SignalStrength(val cycle: Int, val x: Int)

data class State(
    val x: Int = 1,
    val cycle: Int = 1,
    val signalStrengths: List<SignalStrength> = emptyList(),
    val executions: List<Execution> = emptyList()
)

object Task1 {
    fun sumOfSignalStrengths(input: String): Int {
        val instructions = input
            .lines()
            .map { it.toInstruction() }

        val state = instructions.fold(State()) { state, instruction ->
            val executionsAtThisCycle = state.executions.filter { it.atCycle == state.cycle }
            val remainingExecutions = state.executions.filterNot { it.atCycle == state.cycle }
            val x = state.x + executionsAtThisCycle.sumOf { it.instruction.value }
            val signalStrengths = state.signalStrengths + SignalStrength(state.cycle, x)

            if (instruction is AddX) {
                State(x, state.cycle + instruction.cycles, signalStrengths, remainingExecutions + Execution(instruction, state.cycle + instruction.cycles))
            } else {
                State(x, state.cycle + instruction.cycles, signalStrengths, remainingExecutions)
            }
        }

        return signalStrength(state.signalStrengths, 20) + signalStrength(state.signalStrengths, 60) +
                signalStrength(state.signalStrengths, 100) + signalStrength(state.signalStrengths, 140) +
                signalStrength(state.signalStrengths, 180) + signalStrength(state.signalStrengths, 220)
    }

    private fun signalStrength(signalStrengths: List<SignalStrength>, atCycle: Int): Int {
        return signalStrengths.last { it.cycle <= atCycle }.x * atCycle
    }
}

fun String.toInstruction(): Instruction {
    return when (split(" ")[0]) {
        "noop" -> Noop
        "addx" -> AddX(split(" ")[1].toInt())
        else -> error("Incorrect instruction: $this")
    }
}