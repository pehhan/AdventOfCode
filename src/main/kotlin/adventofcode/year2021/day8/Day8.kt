package adventofcode.year2021.day8

typealias Pattern = String
typealias OutputValue = String

data class Entry(val signalPatterns: List<Pattern>, val outputValues: List<OutputValue>)

object Task1 {
    fun occurrences(lines: List<String>): Int {
        return lines
            .map { Entry(it.substringBefore(" | ").split(" "), it.substringAfter(" | ").split(" ")) }
            .flatMap { it.outputValues }
            .count { it.length == 2 || it.length == 3 || it.length == 4 || it.length == 7 }
    }
}

object Task2 {
    fun sumOfOutputValues(lines: List<String>): Int {
        val entries = lines.map { Entry(it.substringBefore(" | ").split(" "), it.substringAfter(" | ").split(" ")) }

        println(entries)

        return 1
    }
}