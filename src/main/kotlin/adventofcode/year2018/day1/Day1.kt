package adventofcode.year2018.day1

typealias Frequency = Int

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

object Task2 {

    fun resultingFrequency(input: String): Int {
        val changes = input
            .lines()
            .map { it.toInt() }

        return findDuplicateFrequency(changes, setOf(0), 0)
    }

    private fun findDuplicateFrequency(changes: List<Frequency>, frequencies: Set<Frequency>, start: Frequency): Frequency {
        var current = start
        val foundFrequencies = frequencies.toMutableSet()

        for (change in changes) {
            current += change

            if (current in foundFrequencies) {
                return current
            } else {
                foundFrequencies += current
            }
        }

        return findDuplicateFrequency(changes, foundFrequencies, current)
    }
}