package adventofcode.year2021.day14

fun result(input: String, steps: Int): Long {
    val (template, rawRules) = input.split("\n\n")
    val rules = parseRules(rawRules)
    val occurrences = template.groupingBy { it }.eachCount().mapValues { it.value.toLong() }.toMutableMap()
    var pairs = template.windowed(2).groupingBy { it }.eachCount().mapValues { it.value.toLong() }

    repeat(steps) {
        val newPairs = mutableMapOf<String, Long>()

        for (pair in pairs) {
            val replacement = rules[pair.key] ?: continue

            occurrences[replacement] = (occurrences[replacement] ?: 0) + pair.value

            val key1 = "${pair.key.first()}$replacement"
            val key2 = "$replacement${pair.key.last()}"

            newPairs[key1] = (newPairs[key1] ?: 0) + pair.value
            newPairs[key2] = (newPairs[key2] ?: 0) + pair.value
        }

        pairs = newPairs
    }

    return occurrences.maxOf { it.value } - occurrences.minOf { it.value }
}

private fun parseRules(input: String): Map<String, Char> {
    val rules = mutableMapOf<String, Char>()

    for (line in input.lines()) {
        val (from, to) = line.split(" -> ")
        rules[from] = to.first()
    }

    return rules
}