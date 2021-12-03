package adventofcode.year2021.day3

object Task1 {
    fun powerConsumption(lines: List<String>): Int {
        val gammaRate = rate(lines, ::mostCommonBit)
        val epsilonRate = rate(lines, ::leastCommonBit)

        return gammaRate * epsilonRate
    }

    private fun rate(input: List<String>, rating: (List<Char>) -> Char): Int {
        return input
            .map { it.toList() }
            .transposed()
            .map { rating(it) }
            .joinToString("")
            .toInt(2)
    }
}

object Task2 {
    fun lifeSupportRating(lines: List<String>): Int {
        val oxygenGeneratorRating = rate(lines, ::mostCommonBit)
        val scrubberRating = rate(lines, ::leastCommonBit)

        return oxygenGeneratorRating * scrubberRating
    }

    private fun rate(input: List<String>, rating: (List<Char>) -> Char): Int {
        var result = input.map { it.toList() }
        var index = 0

        while (result.size > 1) {
            val bit = rating(result.transposed()[index])

            result = result.filter { it[index] == bit }
            index++
        }

        return result.flatten().joinToString("").toInt(2)
    }
}

private fun mostCommonBit(chars: List<Char>): Char {
    return if (chars.count { it == '0' } > chars.count { it == '1' }) '0' else '1'
}

private fun leastCommonBit(chars: List<Char>): Char {
    return if (chars.count { it == '0' } > chars.count { it == '1' }) '1' else '0'
}

private fun <T> List<List<T>>.transposed(): List<List<T>> {
    val rowSize = size
    val columnSize = this[0].size
    val transposed = List(columnSize) { mutableListOf<T>() }

    for (i in 0 until rowSize) {
        for (j in 0 until columnSize) {
            transposed[j].add(this[i][j])
        }
    }

    return transposed
}