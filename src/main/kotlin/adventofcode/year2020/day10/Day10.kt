package adventofcode.year2020.day10

object Task1 {
    fun result(input: String): Int {
        val diffs = "0\n$input"
            .lines()
            .map { it.toInt() }
            .sorted()
            .zipWithNext { a, b -> b - a }

        return diffs.count { it == 1 } * (diffs.count { it == 3 } + 1)
    }
}

object Task2 {
    fun result(input: String): Long {
        val joltages = input
            .lines()
            .map { it.toInt() }
            .sorted()

        val solutions = joltages.fold(mapOf(0 to 1L)) { map, joltage ->
            map + (joltage to (map[joltage - 1] ?: 0) + (map[joltage - 2] ?: 0) + (map[joltage - 3] ?: 0))
        }

        return solutions[joltages.last()] ?: -1
    }
}