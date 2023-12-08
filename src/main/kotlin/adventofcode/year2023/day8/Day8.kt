package adventofcode.year2023.day8

enum class Instruction {
    Left,
    Right
}

typealias Node = String
typealias NodeMap = Map<Node, Pair<Node, Node>>

object Task1 {
    fun steps(input: String): Int {
        val instructions = input.toInstructions()
        val nodeMap = input.toNodeMap()

        return steps(instructions, nodeMap)
    }

    private fun steps(instructions: List<Instruction>, nodeMap: NodeMap): Int {
        var steps = 0
        var instructionIndex = 0
        var node = "AAA"
        val endNode = "ZZZ"

        while (node != endNode) {
            val instruction = instructions[instructionIndex]
            val map = nodeMap[node] ?: break

            node = when (instruction) {
                Instruction.Left -> map.first
                Instruction.Right -> map.second
            }

            instructionIndex = (instructionIndex + 1) % instructions.size
            steps++
        }

        return steps
    }
}

private fun String.toInstructions(): List<Instruction> {
    return lines().first().map {
        when (it) {
            'L' -> Instruction.Left
            'R' -> Instruction.Right
            else -> throw IllegalArgumentException("Unexpected instruction: $it")
        }
    }
}

private fun String.toNodeMap(): NodeMap {
    return this
        .lines()
        .drop(2)
        .associate {
            val key = it.split(" = ").first()
            val values = it.split(" = ").last().replace("(", "").replace(")", "").split(", ")
            val leftValue = values.first()
            val rightValue = values.last()

            key to Pair(leftValue, rightValue)
        }
}