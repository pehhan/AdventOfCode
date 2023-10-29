package adventofcode.year2018.day2

object Task1 {
    fun checksum(input: String): Int {
        return input
            .lines()
            .map { it.groupingBy { char -> char }.eachCount() }
            .checksum()
    }

    private fun List<Map<Char, Int>>.checksum(): Int {
        return numberByCount(2) * numberByCount(3)
    }

    private fun List<Map<Char, Int>>.numberByCount(count: Int): Int {
        return map { group -> group.filter { it.value == count } }.filter { it.values.isNotEmpty() }.size
    }
}