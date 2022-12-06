package adventofcode.year2022.day6

object Task1 {
    fun markerPosition(input: String) = markerPosition(input, 4)
}

object Task2 {
    fun markerPosition(input: String) = markerPosition(input, 14)
}

private fun markerPosition(input: String, n: Int): Int {
    input.forEachIndexed { index, _ ->
        if (characterSet(input, index, n).size == n) {
            return index + n
        }
    }

    throw IllegalArgumentException("Did not find marker in text.")
}

private fun characterSet(input: String, start: Int, n: Int): Set<Char> {
    return input.substring(start, start + n).toSet()
}