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
}

object Task2 {
    fun steps(input: String): Long {
        val instructions = input.toInstructions()
        val nodeMap = input.toNodeMap()

        val startNodes = nodeMap.map { it.key }.filter { it.last() == 'A' }

        println(startNodes)

        return steps(instructions, nodeMap, startNodes)
    }
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

private fun steps(instructions: List<Instruction>, nodeMap: NodeMap, startNodes: List<Node>): Long {
    var steps = 0L
    var instructionIndex = 0
    var nodes = startNodes.toMutableList()

    while (!nodes.areAllNodesEndNodes()) {
        val instruction = instructions[instructionIndex]
        val newNodes = mutableListOf<Node>()

        for (node in nodes) {
            val map = nodeMap[node] ?: break

            val newNode = when (instruction) {
                Instruction.Left -> map.first
                Instruction.Right -> map.second
            }

            newNodes += newNode
        }

        nodes = newNodes

        instructionIndex = (instructionIndex + 1) % instructions.size
        steps++

        if (steps % 10_0000_000 == 0L) {
            println(nodes)
            println(steps)
        }
    }

    return steps
}

private fun List<Node>.areAllNodesEndNodes(): Boolean {
    return this.all { it.last() == 'Z' }
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