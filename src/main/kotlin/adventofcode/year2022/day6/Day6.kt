package adventofcode.year2022.day6

object Task1 {
    fun markerPosition(input: String) = markerPosition(input, 4)
}

object Task2 {
    fun markerPosition(input: String) = markerPosition(input, 14)
}

private fun markerPosition(input: String, n: Int): Int {
    return input
        .windowed(n)
        .withIndex()
        .first { it.value.toSet().size == n }
        .index + n
}