package adventofcode.year2022.day4

object Task1 {
    fun numberOfAssignmentPairs(input: String): Int {
        return numberOfAssignmentPairs(input, IntRange::covers)
    }
}

object Task2 {
    fun numberOfAssignmentPairs(input: String): Int {
        return numberOfAssignmentPairs(input, IntRange::overlaps)
    }
}

fun numberOfAssignmentPairs(input: String, func: IntRange.(IntRange) -> Boolean): Int {
    return input
        .lines()
        .map { it.split(",") }
        .map { pair -> pair.map { it.toRange() } }
        .count { pair -> pair[0].func(pair[1]) || pair[1].func(pair[0]) }
}

fun String.toRange(): IntRange {
    return IntRange(split("-")[0].toInt(), split("-")[1].toInt())
}

fun IntRange.covers(otherRange: IntRange): Boolean {
    return otherRange.first >= first && otherRange.last <= last
}

fun IntRange.overlaps(otherRange: IntRange): Boolean {
    return otherRange.first in first..last || otherRange.last in first..last
}