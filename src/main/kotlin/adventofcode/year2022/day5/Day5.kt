package adventofcode.year2022.day5

data class Step(val n: Int, val from: Int, val to: Int) {

    companion object {
        fun from(line: String): Step {
            val words = line.split(" ")

            return Step(
                n = words[1].toInt(),
                from = words[3].toInt() - 1,
                to = words[5].toInt() - 1
            )
        }
    }
}

typealias Crate = Char
typealias Stacks = List<ArrayDeque<Crate>>

object Task1 {
    fun cratesOnTop(input: String): String {
        return input
            .split("\n\n")[1]
            .lines()
            .map { Step.from(it) }
            .fold(initialStacks()) { stacks, step ->
                repeat(step.n) {
                    stacks[step.to].addLast(stacks[step.from].removeLast())
                }

                stacks
            }
            .map { it.last() }
            .joinToString("")
    }
}

object Task2 {
    fun cratesOnTop(input: String): String {
        return input
            .split("\n\n")[1]
            .lines()
            .map { Step.from(it) }
            .fold(initialStacks()) { stacks, step ->
                val fromStack = stacks[step.from]
                val cratesToMove = fromStack.subList(fromStack.size - step.n, fromStack.size)

                stacks[step.to].addAll(cratesToMove)

                repeat(step.n) {
                    fromStack.removeLast()
                }

                stacks
            }
            .map { it.last() }
            .joinToString("")
    }
}

// Should be parsed nicely, but I couldn't be bothered.
private fun initialStacks(): Stacks {
    return listOf(
        ArrayDeque(listOf('H', 'T', 'Z', 'D')),
        ArrayDeque(listOf('Q', 'R', 'W', 'T', 'G', 'C', 'S')),
        ArrayDeque(listOf('P', 'B', 'F', 'Q', 'N', 'R', 'C', 'H')),
        ArrayDeque(listOf('L', 'C', 'N', 'F', 'H', 'Z')),
        ArrayDeque(listOf('G', 'L', 'F', 'Q', 'S')),
        ArrayDeque(listOf('V', 'P', 'W', 'Z', 'B', 'R', 'C', 'S')),
        ArrayDeque(listOf('Z', 'F', 'J')),
        ArrayDeque(listOf('D', 'L', 'V', 'Z', 'R', 'H', 'Q')),
        ArrayDeque(listOf('B', 'H', 'G', 'N', 'F', 'Z', 'L', 'D'))
    )
}