package adventofcode.year2018.day1

object Task1 {
    fun resultingFrequency(input: String): Int {
        return input
            .lines()
            .map { it.toInt() }
            .fold(0) { result, frequency ->
                result + frequency
            }
    }
}