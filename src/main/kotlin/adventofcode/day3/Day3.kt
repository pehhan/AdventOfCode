package adventofcode.day3

object Task1 {
    fun numberOfTrees(lines: List<String>): Long {
        return numberOfTrees(lines, 3, 1)
    }
}

object Task2 {
    fun numberOfTrees(lines: List<String>): Long {
        return numberOfTrees(lines, 1, 1) * numberOfTrees(lines, 3, 1) * numberOfTrees(lines, 5, 1) * numberOfTrees(lines, 7, 1) * numberOfTrees(lines, 1, 2)
    }
}

private fun numberOfTrees(lines: List<String>, slopeRight: Int, slopeDown: Int): Long {
    var count = 0L
    var previousPosition = 0

    for (i in slopeDown until lines.size step slopeDown) {
        val line = lines[i]
        val position = (previousPosition + slopeRight) % line.length
        if (line[position] == '#') count++
        previousPosition = position
    }

    return count
}
