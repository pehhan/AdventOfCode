package adventofcode.year2020.day19

import java.lang.IllegalArgumentException

object Task1 {
    fun numberOfMatchingMessages(input: String): Int {
        return numberOfMatchingMessages(input, null)
    }
}

object Task2 {
    fun numberOfMatchingMessages(input: String): Int {
        val replacements = mapOf("8" to "42 | 42 8", "11" to "42 31 | 42 11 31")
        return numberOfMatchingMessages(input, replacements)
    }
}

private fun numberOfMatchingMessages(input: String, replacements: Map<String, String>?): Int {
    val (rawRules, messages) = input.split("\n\n")

    val rules = rawRules
        .lines()
        .map { it.substringBefore(": ") to it.substringAfter(": ") }
        .toMap()
        .toMutableMap()

    if (replacements != null) {
        for (entry in replacements) {
            rules[entry.key] = entry.value
        }
    }

    return messages
        .lines()
        .count { matches(rules, it, listOf("0")) }
}

private fun matches(rules: Map<String, String>, message: String, messageRules: List<String>): Boolean {
    if (message.isEmpty()) return messageRules.isEmpty()
    if (messageRules.isEmpty()) return false

    val value = rules[messageRules[0]] ?: throw IllegalArgumentException("Could not find value for key: ${messageRules[0]}")

    if (value[0] == '"') {
        return if (message[0] == value[1]) {
            matches(rules, message.drop(1), messageRules.drop(1))
        } else {
            false
        }
    }
    return value.split(" | ").firstOrNull {
        matches(rules, message, it.split(" ") + messageRules.drop(1))
    } != null
}