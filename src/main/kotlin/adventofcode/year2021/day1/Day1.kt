package adventofcode.year2021.day1

object Task1 {
    fun numberOfIncreases(lines: List<Int>): Int {
        return lines.numberOfIncreases()
    }
}

object Task2 {
    fun numberOfIncreases(lines: List<Int>): Int {
        return lines.windowed(3) { it.sum() }.numberOfIncreases()
    }
}

private fun List<Int>.numberOfIncreases(): Int {
    return zipWithNext().count { it.second > it.first }
}