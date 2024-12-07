package adventofcode.year2024.day3

object Task1 {
    fun result(input: String): Int {
        val regex = """mul\([0-9]{1,3},[0-9]{1,3}\)""".toRegex()
        val matches = regex.findAll(input).map { it.value }.toList()

        return matches
            .map { Multiplication.from(it) }
            .sumOf { it.result() }
    }
}

object Task2 {
    fun result(input: String): Int {
        val regex = """mul\([0-9]{1,3},[0-9]{1,3}\)|do\(\)|don\'t\(\)""".toRegex()
        val matches = regex.findAll(input).map { it.value }.toList()

        return matches.toOperationList().result()
    }
}

sealed interface Operation

data class Multiplication(val a: Int, val b: Int) : Operation {

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

data object Do : Operation
data object Dont : Operation

private fun List<String>.toOperationList(): List<Operation> {
    return map {
        when {
            it.startsWith("mul") -> Multiplication.from(it)
            it.startsWith("don") -> Dont
            it.startsWith("do") -> Do
            else -> throw IllegalArgumentException("Invalid operation: $it")
        }
    }
}

data class OperationResult(val value: Int, val on: Boolean)

private fun List<Operation>.result(): Int {
    val initial = OperationResult(0, true)
    return fold(initial) { result, op ->
        when (op) {
            Do -> OperationResult(result.value, true)
            Dont -> OperationResult(result.value, false)
            is Multiplication -> {
                when (result.on) {
                    true -> OperationResult(result.value + op.result(), true)
                    false -> result
                }
            }
        }
    }.value
}