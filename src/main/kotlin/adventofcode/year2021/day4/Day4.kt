package adventofcode.year2021.day4

data class BingoNumber(val value: Int, var marked: Boolean = false)

data class BingoBoard(private val numbers: List<List<BingoNumber>>) {

    fun mark(number: Int) {
        for (i in numbers.indices) {
            for (j in numbers[i].indices) {
                if (numbers[i][j].value == number) {
                    numbers[i][j].marked = true
                }
            }
        }
    }

    fun hasBingo(): Boolean {
        return numbers.any { list -> list.all { it.marked } } ||
                numbers.transpose().any { list -> list.all { it.marked } }
    }

    fun score(): Int {
        return numbers.flatten().filterNot { it.marked }.sumOf { it.value }
    }

    companion object {
        fun fromLines(input: String): BingoBoard {
            val numbers = input
                .split("\n")
                .map { it.trim().split("\\s+".toRegex()) }
                .map { list -> list.map { BingoNumber(it.toInt()) } }

            return BingoBoard(numbers)
        }
    }
}

object Task1 {
    fun score(input: String): Int {
        val (numbersToDraw, boards) = parseInput(input)

        val drawnNumbers = numbersToDraw.takeWhile { number ->
            boards.forEach { it.mark(number) }
            boards.none { it.hasBingo() }
        }

        val winningBoard = boards.first { it.hasBingo() }

        return winningBoard.score() * numbersToDraw[drawnNumbers.size]
    }
}

object Task2 {
    fun score(input: String): Int {
        val (numbersToDraw, boards) = parseInput(input)

        var lastBoardToWin = boards[0]

        val drawnNumbers = numbersToDraw.takeWhile { number ->
            boards.forEach { board ->
                val bingoBefore = board.hasBingo()
                board.mark(number)
                if (!bingoBefore && board.hasBingo()) {
                    lastBoardToWin = board
                }
            }
            boards.any { !it.hasBingo() }
        }

        return lastBoardToWin.score() * numbersToDraw[drawnNumbers.size]
    }
}

private fun parseInput(input: String): Pair<List<Int>, List<BingoBoard>> {
    val data = input.split("\n\n")
    val numbersToDraw = data.first().split(",").map { it.toInt() }
    val boards = data.drop(1).map { BingoBoard.fromLines(it) }
    return Pair(numbersToDraw, boards)
}

private fun <T> List<List<T>>.transpose(): List<List<T>> {
    val rowSize = size
    val columnSize = this[0].size
    val transposed = List(columnSize) { mutableListOf<T>() }

    for (i in 0 until rowSize) {
        for (j in 0 until columnSize) {
            transposed[j].add(this[i][j])
        }
    }

    return transposed
}