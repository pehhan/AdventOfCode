package adventofcode.year2020.day5

typealias BoardingPass = String

object Task1 {
    fun highestSeatId(input: String): Int {
        return input
            .lines()
            .maxOf { seatId(it) }
    }
}

object Task2 {
    fun findFreeSeatId(input: String): Int {
        return seatId(findMissingBoardingPass(input.lines()))
    }

    private fun findMissingBoardingPass(boardingPasses: List<BoardingPass>): BoardingPass {
        return (0..127)
            .flatMap { row -> (0..7)
                .filter { column -> !hasBoardingPassAt(boardingPasses, row, column) }
                .map { column -> boardingPass(row, column) }
                .filter { boardingPass -> seatsNextToSeatOccupied(boardingPasses, boardingPass) }
            }
            .first()
    }

    private fun hasBoardingPassAt(boardingPasses: List<BoardingPass>, row: Int, column: Int): Boolean {
        return boardingPasses.contains(boardingPass(row, column))
    }

    private fun boardingPass(row: Int, column: Int): BoardingPass {
        return boardingPassDetails(7, 0..127, row, 'F', 'B') + boardingPassDetails(3, 0..7, column, 'L', 'R')
    }

    private fun boardingPassDetails(n: Int, initialRange: IntRange, value: Int, lower: Char, upper: Char): String {
        var range = initialRange.toList()
        return (0 until n).map {
            when (value) {
                in range.takeFirstHalf() -> {
                    range = range.takeFirstHalf()
                    lower
                }
                else -> {
                    range = range.dropFirstHalf()
                    upper
                }
            }
        }.joinToString("")
    }

    private fun seatsNextToSeatOccupied(boardingPasses: List<BoardingPass>, boardingPass: BoardingPass): Boolean {
        val seatId = seatId(boardingPass)
        return boardingPasses.map { seatId(it) }.containsAll(listOf(seatId - 1, seatId + 1))
    }
}

private fun seatId(boardingPass: BoardingPass): Int {
    return row(boardingPass) * 8 + column(boardingPass)
}

private fun row(boardingPass: BoardingPass): Int {
    return positionInRange(boardingPass, 0..127, 0..6, 'F', 'B')
}

private fun column(boardingPass: BoardingPass): Int {
    return positionInRange(boardingPass, 0..7, 7..9, 'L', 'R')
}

private fun positionInRange(boardingPass: BoardingPass, seatRange: IntRange, passRange: IntRange, lower: Char, upper: Char): Int {
    var range = seatRange.toList()

    for (i in passRange) {
        when (boardingPass[i]) {
            lower -> range = range.takeFirstHalf()
            upper -> range = range.dropFirstHalf()
        }
    }

    return range.first()
}

fun <T> List<T>.takeFirstHalf(): List<T> {
    return take(size / 2)
}

fun <T> List<T>.dropFirstHalf(): List<T> {
    return drop(size / 2)
}