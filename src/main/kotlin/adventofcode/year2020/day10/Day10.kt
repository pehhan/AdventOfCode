package adventofcode.year2020.day10

object Task1 {
    fun result(input: String): Int {
        return input
            .lines()
            .map { it.toInt() }
            .sorted()
            .let { 0 + it + (it.last() + 3) }
            .zipWithNext { a, b -> b - a }
            .let { list -> list.count { it == 1 } * list.count { it == 3 } }
    }
}

object Task2 {
    fun result(input: String): Long {
        val joltages = input
            .lines()
            .map { it.toInt() }
            .sorted()
            .let { it + (it.last() + 3) }

        val solutions = joltages.fold(mapOf(0 to 1L)) { map, joltage ->
            map + (joltage to (map[joltage - 1] ?: 0) + (map[joltage - 2] ?: 0) + (map[joltage - 3] ?: 0))
        }

        return solutions[joltages.last()] ?: -1
    }
}

private operator fun Int.plus(list: List<Int>): List<Int> {
    return listOf(this) + list
}