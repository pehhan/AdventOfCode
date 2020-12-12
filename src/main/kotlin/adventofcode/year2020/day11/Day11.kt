package adventofcode.year2020.day11

import adventofcode.year2020.day11.SeatStatus.*
import java.lang.IllegalArgumentException

enum class SeatStatus {
    Floor, Empty, Occupied
}

typealias Seating = List<List<SeatStatus>>

private fun toSeatStatus(char: Char): SeatStatus {
    return when (char) {
        '.' -> Floor
        'L' -> Empty
        '#' -> Occupied
        else -> throw IllegalArgumentException("Illegal character: $char")
    }
}

object Task1 {
    fun seatsOccupied(input: String): Int {
        return seatsOccupied(input, 4, ::numberOfOccupiedSeatsAdjacent)
    }

    private fun numberOfOccupiedSeatsAdjacent(seating: Seating, x: Int, y: Int): Int {
        var count = 0

        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) continue
                if (!withinBounds(seating, x, y, i, j)) continue

                count += if (isSeatOccupied(seating, x, y, i, j)) 1 else 0
            }
        }

        return count
    }

    private fun isSeatOccupied(seating: Seating, x: Int, y: Int, diffX: Int, diffY: Int): Boolean {
        return seating[x + diffX][y + diffY] == Occupied
    }
}

object Task2 {
    fun seatsOccupied(input: String): Int {
        return seatsOccupied(input, 5, ::numberOfVisibleOccupiedSeats)
    }

    private fun numberOfVisibleOccupiedSeats(seating: Seating, x: Int, y: Int): Int {
        var count = 0

        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) continue
                if (!withinBounds(seating, x, y, i, j)) continue

                count += if (findFirstSeatForDirection(seating, x, y, i, j) == Occupied) 1 else 0
            }
        }

        return count
    }

    private fun findFirstSeatForDirection(seating: Seating, x: Int, y: Int, diffX: Int, diffY: Int): SeatStatus {
        if (!withinBounds(seating, x, y, diffX, diffY)) return Floor

        return when (val status = seating[x + diffX][y + diffY]) {
            Empty, Occupied -> status
            Floor -> findFirstSeatForDirection(seating, x + diffX, y + diffY, diffX, diffY)
        }

    }
}

private fun seatsOccupied(input: String, neighbourLimit: Int, occupiedSeatsFunc: (Seating, Int, Int) -> (Int)): Int {
    var previousStep = input
        .lines()
        .map { str -> str.map { toSeatStatus(it) } }

    var nextStep = nextStep(previousStep, neighbourLimit, occupiedSeatsFunc)

    while (previousStep != nextStep) {
        previousStep = nextStep
        nextStep = nextStep(previousStep, neighbourLimit, occupiedSeatsFunc)
    }

    return nextStep.flatten().count { it == Occupied }
}

private fun nextStep(seating: Seating, neighbourLimit: Int, occupiedSeatsFunc: (Seating, Int, Int) -> (Int)): Seating {
    val nextSeating = seating.map { it.toMutableList() }

    for (x in seating.indices) {
        for (y in seating[x].indices) {
            when (seating[x][y]) {
                Empty -> {
                    if (occupiedSeatsFunc(seating, x, y) == 0) {
                        nextSeating[x][y] = Occupied
                    }
                }
                Occupied -> {
                    if (occupiedSeatsFunc(seating, x, y) >= neighbourLimit) {
                        nextSeating[x][y] = Empty
                    }
                }
            }
        }
    }

    return nextSeating
}

private fun withinBounds(seating: Seating, x: Int, y: Int, diffX: Int, diffY: Int): Boolean {
    return x + diffX >= 0 && x + diffX < seating.size && y + diffY >= 0 && y + diffY < seating[x].size
}