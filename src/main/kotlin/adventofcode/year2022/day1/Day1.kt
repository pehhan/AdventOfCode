package adventofcode.year2022.day1

object Task1 {
    fun caloriesOfTopOne(input: String): Int? {
        return caloriesOfTopN(input, 1)
    }
}

object Task2 {
    fun caloriesOfTopThree(input: String): Int {
        return caloriesOfTopN(input, 3)
    }
}

private fun caloriesOfTopN(input: String, n: Int): Int {
    return input
        .split("\n\n")
        .map { it.lines() }
        .map { line -> line.map { it.toInt() } }
        .map { line -> line.sum() }
        .sorted()
        .takeLast(n)
        .sum()
}