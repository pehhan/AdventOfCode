package adventofcode.year2021.day8

object Task1 {
    fun occurrences(lines: List<String>): Int {
        return lines
            .flatMap { it.substringAfter(" | ").split(" ") }
            .count { it.length == 2 || it.length == 3 || it.length == 4 || it.length == 7 }
    }
}

object Task2 {
    fun sumOfOutputValues(lines: List<String>): Int {
        return lines
            .asSequence()
            .map { it.substringBefore(" | ").split(" ") to it.substringAfter(" | ").split(" ") }
            .map { entry -> mapSegments(entry.first.map { it.toSet() }.groupBy { it.size }) to entry.second }
            .map { entry -> entry.second.map { entry.first.indexOf(it.toSet()) } }
            .sumOf { it.joinToString("").toInt() }
    }

    private fun mapSegments(segments: Map<Int, List<Set<Char>>>): List<Set<Char>> {
        val result = MutableList(10) { setOf<Char>() }

        // The easy ones
        result[1] = segments[2]!!.first()
        result[4] = segments[4]!!.first()
        result[7] = segments[3]!!.first()
        result[8] = segments[7]!!.first()

        // With 5 segments
        result[3] = segments[5]!!.first { it.containsAll(result[1]) }
        result[2] = segments[5]!!.first { it + result[4] == result[8] }
        result[5] = segments[5]!!.first { it != result[3] && it != result[2] }

        // With 6 segments
        result[9] = segments[6]!!.first { it.containsAll(result[3]) }
        result[0] = segments[6]!!.first { it.containsAll(result[1]) && it != result[9] }
        result[6] = segments[6]!!.first { it != result[9] && it != result[0] }

        return result
    }
}