package adventofcode.year2023.day9

object Task1 {
    fun sum(input: String): Int {
        val lines = input
            .lines()
            .map { it.split(" ") }
            .map { list -> list.map { it.toInt() } }

        return sumOfExtrapolatedValues(lines)
    }

    private fun sumOfExtrapolatedValues(lines: List<List<Int>>): Int {
        var sum = 0

        for (line in lines) {
            val lists = mutableListOf<List<Int>>()
            lists += line

            var newList = line

            while (!newList.all { it == 0 }) {
                newList = newList.windowed(2).map { it.last() - it.first() }
                lists += newList
            }

            sum += extrapolateLastValue(lists)
        }

        return sum
    }

    private fun extrapolateLastValue(lists: List<List<Int>>): Int {
        return lists.sumOf { it.last() }
    }
}