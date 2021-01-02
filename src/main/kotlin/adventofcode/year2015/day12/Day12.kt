package adventofcode.year2015.day12

import com.google.gson.*

object Task1 {
    fun sumOfNumbers(input: String): Int {
        val element = JsonParser.parseString(input)
        return sumOfNumbers(element, false)
    }
}

object Task2 {
    fun sumOfNumbers(input: String): Int {
        val element = JsonParser.parseString(input)
        return sumOfNumbers(element, true)
    }
}

private fun sumOfNumbers(json: JsonElement, redIsWrong: Boolean): Int {
    var sum = 0

    when {
        json.isJsonObject -> {
            val obj = json.asJsonObject
            if (redIsWrong) {
                if (!hasRed(obj)) {
                    sum += obj.entrySet().map { sumOfNumbers(it.value, redIsWrong) }.sum()
                }
            } else {
                sum += obj.entrySet().map { sumOfNumbers(it.value, redIsWrong) }.sum()
            }
        }
        json.isJsonArray -> {
            sum += json.asJsonArray.map { sumOfNumbers(it, redIsWrong) }.sum()
        }
        json.isJsonPrimitive -> {
            val primitive =  json.asJsonPrimitive
            if (primitive.isNumber) {
                sum += primitive.asInt
            }
        }
    }

    return sum
}

private fun hasRed(obj: JsonObject): Boolean {
    return obj.entrySet()
        .asSequence()
        .map { it.value }
        .filter { it.isJsonPrimitive }
        .map { it.asJsonPrimitive }
        .filter { it.isString }
        .map { it.asString }
        .any { it == "red" }
}