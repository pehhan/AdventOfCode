package adventofcode.year2023.day9

object Task1 {
    fun sum(input: String): Int {
        val lines = parseToLines(input)
        return sumOfExtrapolatedValues(lines, ::extrapolateLastValue)
    }

    private fun extrapolateLastValue(lists: List<List<Int>>): Int {
        return lists.sumOf { it.last() }
    }
}

object Task2 {
    fun sum(input: String): Int {
        val lines = parseToLines(input)
        return sumOfExtrapolatedValues(lines, ::extrapolateFirstValue)
    }

    private fun extrapolateFirstValue(lists: List<List<Int>>): Int {
        return lists.reversed().drop(1).fold(0) { acc, values ->
            values.first() - acc
        }
    }
}

private fun parseToLines(input: String): List<List<Int>> {
    return input
        .lines()
        .map { it.split(" ") }
        .map { list -> list.map { it.toInt() } }
}

private fun sumOfExtrapolatedValues(lines: List<List<Int>>, extrapolateFunc: (List<List<Int>>) -> Int): Int {
    return lines.fold(0) { acc, line ->
        val lists = mutableListOf<List<Int>>()
        lists += line

        var newList = line

        while (!newList.all { it == 0 }) {
            newList = newList.windowed(2).map { it.last() - it.first() }
            lists += newList
        }

        acc + extrapolateFunc(lists)
    }
}