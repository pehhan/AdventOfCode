package adventofcode.year2023.day8

enum class Instruction {
    Left,
    Right
}

typealias Node = String
typealias NodeMap = Map<Node, Pair<Node, Node>>

object Task1 {
    fun steps(input: String): Long {
        val instructions = input.toInstructions()
        val nodeMap = input.toNodeMap()

        return steps(instructions, nodeMap, startNode = "AAA", endNodeSuffix = "ZZZ")
    }
}

object Task2 {
    fun steps(input: String): Long {
        val instructions = input.toInstructions()
        val nodeMap = input.toNodeMap()
        val startNodes = nodeMap.map { it.key }.filter { it.last() == 'A' }

        return steps(instructions, nodeMap, startNodes)
    }

    private fun steps(instructions: List<Instruction>, nodeMap: NodeMap, startNodes: List<Node>): Long {
        val stepList = startNodes.fold(emptyList<Long>()) { acc, node ->
            acc + steps(instructions, nodeMap, startNode = node, endNodeSuffix = "Z")
        }

        return leastCommonMultiplier(stepList)
    }

    private fun leastCommonMultiplier(numbers: List<Long>): Long {
        var result = numbers[0]
        for (i in 1 until numbers.size) {
            result = leastCommonMultiplier(result, numbers[i])
        }
        return result
    }

    private fun leastCommonMultiplier(a: Long, b: Long): Long {
        val larger = if (a > b) a else b
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % a == 0L && lcm % b == 0L) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }
}

private fun steps(instructions: List<Instruction>, nodeMap: NodeMap, startNode: Node, endNodeSuffix: String): Long {
    var steps = 0L
    var instructionIndex = 0
    var node = startNode

    while (!node.endsWith(endNodeSuffix)) {
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