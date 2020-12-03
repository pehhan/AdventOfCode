package adventofcode.day3

object Task1 {
    fun numberOfTrees(input: List<String>): Long {
        return numberOfTrees(input, 3, 1)
    }
}

object Task2 {
    fun numberOfTrees(input: List<String>): Long {
        return numberOfTrees(input, 1, 1) * numberOfTrees(input, 3, 1) * numberOfTrees(input, 5, 1) * numberOfTrees(input, 7, 1) * numberOfTrees(input, 1, 2)
    }
}

private fun numberOfTrees(input: List<String>, slopeRight: Int, slopeDown: Int): Long {
    var count = 0L
    var previousPosition = 0

    for (i in slopeDown until input.size step slopeDown) {
        val line = input[i]
        val position = (previousPosition + slopeRight) % line.length
        if (line[position] == '#') count++
        previousPosition = position
    }

    return count
}
