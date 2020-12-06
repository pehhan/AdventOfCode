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
            .asSequence()
            .map { it.split("\n") }
            .map { list -> list.map { str -> str.map { it } } }
            .map { it.size to it.flatten() }
            .map { list -> list.first to list.second.groupBy { it }}
            .map { list -> list.second.filter { it.value.size == list.first } }
            .sumOf { it.size }
    }
}
