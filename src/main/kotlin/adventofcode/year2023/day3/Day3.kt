package adventofcode.year2023.day3

import kotlin.math.max
import kotlin.math.min

typealias Grid = List<List<Char>>

data class Position(val x: Int, val y: Int)

data class PartNumber(val position: Position, val number: Int)

object Task1 {
    fun sum(input: String): Int {
        return input
            .toGrid()
            .sumOfPartNumbers()
    }
}

fun String.toRow(): List<Char> {
    return toCharArray().toList()
}

fun String.toGrid(): Grid {
    return lines().map { it.toRow() }
}

fun Char.isSymbol(): Boolean {
    return this != '.' && !isDigit()
}

fun Grid.sumOfPartNumbers(): Int {
    val partNumbers = mutableSetOf<PartNumber>()

    for (y in this.indices) {
        for (x in this[y].indices) {
            val char = this[y][x]
            val position = Position(x, y)

            if (char.isDigit() && hasAnySymbolsAround(position)) {
                partNumbers += findFullNumber(position)
            }
        }
    }

    return partNumbers.sumOf { it.number }
}

fun Grid.hasAnySymbolsAround(position: Position): Boolean {
    val yMin = max(position.y - 1, 0)
    val yMax = min(position.y + 1, size - 1)

    val xMin = max(position.x - 1, 0)
    val xMax = min(position.x + 1, this[0].size - 1)

    for (y in yMin..yMax) {
        for (x in xMin..xMax) {
            if (this[y][x].isSymbol()) {
                return true
            }
        }
    }

    return false
}

fun Grid.findFullNumber(position: Position): PartNumber {
    var startIndex = position.x
    var endIndex = position.x

    while (startIndex > 0 && this[position.y][startIndex].isDigit()) {
        startIndex--
    }

    if (startIndex < 0) {
        startIndex++
    }

    if (!this[position.y][startIndex].isDigit()) {
        startIndex++
    }

    while (endIndex < size && this[position.y][endIndex].isDigit()) {
        endIndex++
    }

    if (endIndex >= size) {
        endIndex--
    }

    if (!this[position.y][endIndex].isDigit()) {
        endIndex--
    }

    var number = ""

    for (x in startIndex..endIndex) {
        number += this[position.y][x]
    }

    return PartNumber(Position(startIndex, position.y), number.toInt())
}