package adventofcode.year2020.day16

data class Rule(val name: String, val ranges: List<IntRange>)

object Task1 {
    fun errorRate(input: String): Int {
        val (rawRules, _, nearbyTickets) = input.split("\n\n")
        val rules = parseRules(rawRules)

        return nearbyTickets
            .lines()
            .asSequence()
            .drop(1)
            .map { it.split(",") }
            .map { list -> list.map { it.toInt() } }
            .flatten()
            .filterNot { isNumberInAnyRuleRange(it, rules) }
            .sum()
    }
}

object Task2 {
    fun getNumber(input: String): Long {
        val (rawRules, myTicket, nearbyTickets) = input.split("\n\n")
        val rules = parseRules(rawRules)

        val validTickets = nearbyTickets.lines()
            .drop(1)
            .map { it.split(",") }
            .map { list -> list.map { it.toInt() } }
            .filter { list -> list.all { isNumberInAnyRuleRange(it, rules) } }

        val rowRuleMap = createRowRuleMap(validTickets, rules)
        val filteredRowRules = rowRuleMap.filter { it.value.name.startsWith("departure") }
        val myInts = myTicket.lines()[1].split(",").map { it.toLong() }

        return filteredRowRules.toList().fold(1L) {product, rowRule -> product * myInts[rowRule.first] }
    }

    private fun createRowRuleMap(validTickets: List<List<Int>>, rules: List<Rule>): Map<Int, Rule> {
        // Transpose the tickets (from [[1,2,3],[4,5,6]] to [[1,4],[2,5],[3,6]])
        val remainingRows = validTickets.transposedWithIndex().toMutableList()

        // Find the rules that match each row
        val remainingRules = rules.toMutableList()
        val rowRuleMap = mutableMapOf<Int, Rule>()

        while (remainingRows.isNotEmpty()) {
            val iterator = remainingRows.iterator()
            while (iterator.hasNext()) {
                val row = iterator.next()
                val matchingRules = findMatchingRulesForRows(row.second, remainingRules)

                if (matchingRules.size == 1) {
                    rowRuleMap[row.first] = matchingRules[0]
                    remainingRules.remove(matchingRules[0])
                    iterator.remove()
                }
            }
        }

        return rowRuleMap
    }

    private fun findMatchingRulesForRows(rows: List<Int>, rules: List<Rule>): List<Rule> {
        return rules.filter { rule -> rows.all { number -> rule.ranges.any { range -> range.contains(number) } } }
    }
}

private fun parseRules(input: String): List<Rule> {
    return input
        .lines()
        .map { it.split(": ")[0] to it.split(": ")[1] }
        .map {
            Rule(it.first, it.second.split(" or ").map { range ->
                val (start, end) = range.split("-")
                IntRange(start.toInt(), end.toInt())
            })
        }
}

private fun isNumberInAnyRuleRange(number: Int, rules: List<Rule>): Boolean {
    return rules.any { it.ranges.any { range -> range.contains(number) } }
}

private fun <T> List<List<T>>.transposedWithIndex(): List<Pair<Int, List<T>>> {
    val rowSize = size
    val columnSize = this[0].size

    val transposed = List(columnSize) { Pair(it, mutableListOf<T>()) }

    for (i in 0 until rowSize) {
        for (j in 0 until columnSize) {
            transposed[j].second.add(this[i][j])
        }
    }

    return transposed
}