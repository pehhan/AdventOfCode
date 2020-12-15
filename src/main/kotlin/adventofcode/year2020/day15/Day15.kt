package adventofcode.year2020.day15

data class Index(val last: Int, val nextToLast: Int)

object Task1 {
    fun getNumber(input: String): Int {
        return getNumber(input, 2020)
    }
}

object Task2 {
    fun getNumber(input: String): Int {
        return getNumber(input, 30000000)
    }
}

fun getNumber(input: String, iteration: Int): Int {
    val startingNumbers = input.split(",").map { it.toInt() }
    val result = startingNumbers.mapIndexed { index, number -> number to Index(index, -1) }.toMap().toMutableMap()
    var lastNumber = startingNumbers.last()

    for (i in startingNumbers.size until iteration) {
        val lastNumberIndex = result[lastNumber]!!
        if (lastNumberIndex.nextToLast == -1) {
            val zeroIndex = result[0]!!
            result[0] = Index(i, zeroIndex.last)
            lastNumber = 0
        } else {
            lastNumber = lastNumberIndex.last - lastNumberIndex.nextToLast
            result[lastNumber] = Index(i, result[lastNumber]?.last ?: -1)
        }
    }

    return lastNumber
}