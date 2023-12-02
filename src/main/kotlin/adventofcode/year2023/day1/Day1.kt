package adventofcode.year2023.day1

object Task1 {
    fun sum(input: String): Int {
        val a = input
            .lines()
            .fold(0) { sum, line ->
                sum + line.calibrationValue()
            }

        return a
    }

    private fun String.calibrationValue(): Int {
        val number = first { it.isDigit() }.toString() + last { it.isDigit() }.toString()
        return number.toInt()
    }
}