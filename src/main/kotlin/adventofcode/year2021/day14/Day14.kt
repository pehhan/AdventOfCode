package adventofcode.year2021.day14

fun result(input: String, steps: Int): Int {
    val (template, rawRules) = input.split("\n\n")

    val rules = parseRules(rawRules)
    var polymer = template

    repeat(steps) {
        polymer = processRules(polymer, rules)
        println("Step : $it")
    }

    val occurrences = polymer.groupingBy { it }.eachCount()

    return occurrences.maxOf { it.value } - occurrences.minOf { it.value }
}

private fun parseRules(input: String): Map<String, String> {
    val rules = mutableMapOf<String, String>()

    for (line in input.lines()) {
        val (from, to) = line.split(" -> ")
        rules[from] = to
    }

    return rules
}

private fun processRules(template: String, rules: Map<String, String>): String {
    val polymer = template
        .windowed(2)
        .fold("") { p, element ->
            p + element.first() + rules[element]
        }

    return polymer + template.last()
}