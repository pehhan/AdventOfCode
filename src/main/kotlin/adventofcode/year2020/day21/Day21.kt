package adventofcode.year2020.day21

typealias Ingredient = String
typealias Allergen = String
typealias AllergenMap = Map<Allergen, List<List<Ingredient>>>

object Task1 {
    fun numberOfSafeIngredients(input: String): Int {
        val modifiedInput = input
            .replace("contains ", "")
            .replace(")", "")
            .lines()

        val safeIngredients = findSafeIngredients(allergenMap(modifiedInput)).toList()

        val searchInput = modifiedInput
            .map { it.substringBefore("(") }
            .map { " $it" }
            .joinToString("\n")

        return countOccurrencesOfWords(searchInput, safeIngredients)
    }

    private fun countOccurrencesOfWords(input: String, words: List<String>): Int {
        return words.sumOf { input.count(" $it ") }
    }
}

object Task2 {
    fun getDangerousIngredientList(input: String): String {
        val modifiedInput = input
            .replace("contains ", "")
            .replace(")", "")
            .lines()

        val allergenMap = allergenMap(modifiedInput)
        val safeIngredients = findSafeIngredients(allergenMap)
        val dangerousIngredients = findDangerousIngredients(allergenMap, safeIngredients)

        return resolvedAllergenMap(dangerousIngredients).toSortedMap().values.joinToString(",")
    }

    private fun findDangerousIngredients(allergenMap: AllergenMap, safe: Set<Ingredient>): AllergenMap {
        val map = mutableMapOf<Allergen, List<List<Ingredient>>>()

        for (entry in allergenMap) {
            val dangerous = entry.value.map { list -> list.filterNot { it in safe } }
            map[entry.key] = dangerous
        }

        return map
    }

    private fun resolvedAllergenMap(allergenMap: AllergenMap): Map<Allergen, Ingredient> {
        val resolvedAllergens = mutableMapOf<Allergen, Ingredient>()

        val ingredientSet = allergenMap.values.flatten().flatten().toMutableSet()
        val foundIngredients = mutableListOf<Ingredient>()
        val remainingAllergenMap = allergenMap.toMutableMap()

        while (ingredientSet.isNotEmpty()) {
            val iterator = ingredientSet.iterator()
            while (iterator.hasNext()) {
                val ingredient = iterator.next()

                val filteredAllergens = remainingAllergenMap
                    .map { entry -> entry.key to entry.value.map { list -> list.filterNot { it in foundIngredients } } }
                    .toMap()
                    .filter { entry -> entry.value.all { it.contains(ingredient) } }

                if (filteredAllergens.size == 1) {
                    val allergen = filteredAllergens.keys.first()
                    resolvedAllergens[allergen] = ingredient
                    foundIngredients += ingredient

                    remainingAllergenMap.remove(allergen)
                    iterator.remove()
                }
            }
        }

        return resolvedAllergens
    }
}

private fun allergenMap(input: List<String>): AllergenMap {
    val map = mutableMapOf<Allergen, List<List<Ingredient>>>()

    for (line in input) {
        val (rawIngredients, rawAllergens) = line.split(" (")
        val ingredients = rawIngredients.split(" ")
        val allergens = rawAllergens.split(", ")

        for (allergen in allergens) {
            val currentIngredients = map[allergen]
            if (currentIngredients != null) {
                map[allergen] = currentIngredients + listOf(ingredients)
            } else {
                map[allergen] = listOf(ingredients)
            }
        }
    }

    return map
}

private fun findSafeIngredients(allergenMap: AllergenMap): Set<Ingredient> {
    val dangerous = findPotentiallyDangerousIngredients(allergenMap)
    return allergenMap.values.flatten().flatten().filterNot { it in dangerous }.toSet()
}

private fun findPotentiallyDangerousIngredients(allergenMap: AllergenMap): Set<Ingredient> {
    val dangerous = mutableSetOf<Ingredient>()

    for (entry in allergenMap) {
        val ingredientList = entry.value
        dangerous += ingredientList.flatten().groupBy { it }.filter { it.value.count() == ingredientList.size }.keys.toList()
    }

    return dangerous
}

private fun String.count(element: String): Int {
    var count = 0

    var lastIndex = indexOf(element, 0)
    while (lastIndex >= 0) {
        count += 1
        lastIndex = indexOf(element, lastIndex + 1)
    }

    return count
}