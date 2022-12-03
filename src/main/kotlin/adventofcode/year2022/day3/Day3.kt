package adventofcode.year2022.day3

object Task1 {
    fun prioritySum(input: String): Int {
        return input
            .lines()
            .map { it.splitInTwo() }
            .prioritySum()
    }
}

object Task2 {
    fun prioritySum(input: String): Int {
        return input
            .lines()
            .windowed(3, 3)
            .prioritySum()
    }
}

fun List<List<String>>.prioritySum(): Int {
    return this
        .map { list -> list.map { it.toList() } }
        .map { list -> list.intersection() }
        .flatMap { list -> list.map { it.priority() } }
        .sum()
}

fun List<List<Char>>.intersection(): Set<Char> {
    return fold(first().toSet()) { intersection, list ->
        intersection.intersect(list.toSet())
    }
}

fun String.splitInTwo(): List<String> {
    return listOf(substring(0, length / 2), substring(length / 2))
}

fun Char.priority(): Int {
    return when (this) {
        in 'a'..'z' -> this - 'a' + 1
        in 'A'..'Z' -> this - 'A' + 27
        else -> throw IllegalArgumentException("Invalid character: $this")
    }
}