package adventofcode.year2023.day15

object Task1 {
    fun result(input: String): Int {
        return input.split(",").sumOf { it.hash() }
    }

    private fun String.hash(): Int {
        return fold(0) { acc, char ->
            ((acc + char.code) * 17) % 256
        }
    }
}