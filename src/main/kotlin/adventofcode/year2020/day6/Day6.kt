package adventofcode.year2020.day6

object Task1 {
    fun sumOfPositiveAnswers(input: String): Int {
        return input
            .split("\n\n")
            .map { it.replace("\n", "") }
            .sumOf { it.toSet().size }
    }
}

object Task2 {
    fun sumOfPositiveAnswers(input: String): Int {
        return input
            .split("\n\n")
            .map { it.split("\n") }
            .map { list -> list.map { it.toSet() } }
            .map { it.fold(it[0]) { set, n -> set.intersect(n) } }
            .sumOf { it.size }
    }
}
