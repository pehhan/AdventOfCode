package adventofcode.year2024.day3

data class Multiplication(val a: Int, val b: Int) {

    fun result(): Int {
        return a * b
    }

    companion object {
        fun from(str: String): Multiplication {
            val a = str.substringAfter("mul(").substringBefore(",").toInt()
            val b = str.substringAfter(",").substringBefore(")").toInt()
            return Multiplication(a, b)
        }
    }
}

object Task1 {
    fun result(input: String): Int {
        val regex = """mul\([0-9]{1,3},[0-9]{1,3}\)""".toRegex()
        val matches = regex.findAll(input).map { it.value }.toList()

        return matches
            .map { Multiplication.from(it) }
            .sumOf { it.result() }
    }
}