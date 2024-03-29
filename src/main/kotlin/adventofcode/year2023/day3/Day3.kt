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

object Task2 {
    fun sum(input: String): Int {
        return input
            .toGrid()
            .sumOfGearRatios()
    }
}

private fun String.toGrid(): Grid {
    return lines().map { it.toRow() }
}

private fun String.toRow(): List<Char> {
    return toCharArray().toList()
}

private fun Char.isSymbol(): Boolean {
    return this != '.' && !isDigit()
}

private fun Char.isGear(): Boolean {
    return this == '*'
}

private fun Grid.sumOfPartNumbers(): Int {
    val partNumbers = mutableSetOf<PartNumber>()

    for (y in this.indices) {
        for (x in this[y].indices) {
            val char = this[y][x]
            val position = Position(x, y)

            if (char.isSymbol()) {
                val numbersAround = numbersAround(position)
                partNumbers += numbersAround
            }
        }
    }

    return partNumbers.sumOf { it.number }
}

private fun Grid.sumOfGearRatios(): Int {
    var sum = 0

    for (y in this.indices) {
        for (x in this[y].indices) {
            val char = this[y][x]
            val position = Position(x, y)

            if (char.isGear()) {
                val numbersAround = numbersAround(position)
                sum += numbersAround.gearRatio()
            }
        }
    }

    return sum
}

private fun Set<PartNumber>.gearRatio(): Int {
    return if (size == 2) {
        fold(1) { product, partNumber ->
            product * partNumber.number
        }
    } else {
        0
    }
}

private fun Grid.numbersAround(position: Position): Set<PartNumber> {
    val partNumbers = mutableSetOf<PartNumber>()

    val yMin = max(position.y - 1, 0)
    val yMax = min(position.y + 1, size - 1)

    val xMin = max(position.x - 1, 0)
    val xMax = min(position.x + 1, this[0].size - 1)

    for (y in yMin..yMax) {
        for (x in xMin..xMax) {
            if (this[y][x].isDigit()) {
                partNumbers += findFullNumber(Position(x, y))
            }
        }
    }

    return partNumbers
}

private fun Grid.findFullNumber(position: Position): PartNumber {
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