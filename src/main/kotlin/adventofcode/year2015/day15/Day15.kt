package adventofcode.year2015.day15

import kotlin.math.max

data class Ingredient(
    val name: String,
    val capacity: Int,
    val durability: Int,
    val flavor: Int,
    val texture: Int,
    val calories: Int
) {

    companion object {
        fun from(line: String): Ingredient {
            val (name, rawProperties) = line.split(": ")
            val properties = rawProperties.split(", ")
            val capacity = toPropertyValue(properties[0])
            val durability = toPropertyValue(properties[1])
            val flavor = toPropertyValue(properties[2])
            val texture = toPropertyValue(properties[3])
            val calories = toPropertyValue(properties[4])

            return Ingredient(name, capacity, durability, flavor, texture, calories)
        }

        private fun toPropertyValue(property: String): Int {
            return property.split(" ")[1].toInt()
        }
    }
}

object Task1 {
    fun highestTotalScore(input: String): Int {
        val ingredients = input.lines().map { Ingredient.from(it) }
        val combinations = getPossibleCombinations(100)
        return combinations.map { totalScore(ingredients, it) }.maxOrNull() ?: 0
    }
}

object Task2 {
    fun highestTotalScore(input: String): Int {
        val ingredients = input.lines().map { Ingredient.from(it) }
        val combinations = getPossibleCombinations(100)
        return combinations.map { totalScore(ingredients, it, 500) }.maxOrNull() ?: 0
    }
}

// XXX: This only works if the input has exactly four ingredients
private fun getPossibleCombinations(sum: Int): List<List<Int>> {
    val result = mutableListOf<List<Int>>()

    for (i in 0..sum) {
        for (j in 0..sum) {
            for (k in 0..sum) {
                for (l in 0..sum) {
                    if (i + j + k + l == sum) {
                        result += listOf(i, j, k, l)
                    }
                }
            }
        }
    }

    return result
}

private fun totalScore(ingredients: List<Ingredient>, values: List<Int>, targetCalories: Int? = null): Int {
    assert(ingredients.size == values.size)

    val capacity = values.mapIndexed { index, value -> ingredients[index].capacity * value }.sum().let { max(it, 0) }
    val durability = values.mapIndexed { index, value -> ingredients[index].durability * value }.sum().let { max(it, 0) }
    val flavor = values.mapIndexed { index, value -> ingredients[index].flavor * value }.sum().let { max(it, 0) }
    val texture = values.mapIndexed { index, value -> ingredients[index].texture * value }.sum().let { max(it, 0) }

    return if (targetCalories != null) {
        val calories = values.mapIndexed { index, value -> ingredients[index].calories * value }.sum().let { max(it, 0) }
        if (calories == targetCalories) {
            capacity * durability * flavor * texture
        } else {
            0
        }
    } else {
        capacity * durability * flavor * texture
    }
}