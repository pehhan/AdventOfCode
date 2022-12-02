package adventofcode.year2022.day2

enum class Shape(val points: Int) {
    Rock(1), Paper(2), Scissors(3);

    fun outcomeAgainst(shape: Shape): Outcome {
        return when (shape) {
            winsOver() -> Outcome.Win
            losesTo() -> Outcome.Loss
            else -> Outcome.Draw
        }
    }

    fun winsOver(): Shape {
        return when (this) {
            Rock -> Scissors
            Paper -> Rock
            Scissors -> Paper
        }
    }

    fun losesTo(): Shape {
        return when (this) {
            Rock -> Paper
            Paper -> Scissors
            Scissors -> Rock
        }
    }
}

enum class Outcome(val points: Int) {
    Win(6), Loss(0), Draw(3)
}

data class Round(val opponentShape: Shape, val yourShape: Shape) {

    fun score(): Int {
        return yourShape.points + outcome().points
    }

    private fun outcome(): Outcome {
        return yourShape.outcomeAgainst(opponentShape)
    }

    companion object {

        fun from(line: String): Round {
            return Round(
                opponentShape = line.split(" ")[0].toShape(),
                yourShape = line.split(" ")[1].toShape()
            )
        }
    }
}

data class ShapeWithOutcome(val opponentShape: Shape, val outcome: Outcome) {

    fun toRound(): Round {
        return Round(opponentShape, yourShape())
    }

    private fun yourShape(): Shape {
        return when (outcome) {
            Outcome.Win -> opponentShape.losesTo()
            Outcome.Loss -> opponentShape.winsOver()
            Outcome.Draw -> opponentShape
        }
    }

    companion object {

        fun from(line: String): ShapeWithOutcome {
            return ShapeWithOutcome(
                opponentShape = line.split(" ")[0].toShape(),
                outcome = line.split(" ")[1].toOutcome()
            )
        }
    }
}

object Task1 {
    fun totalScore(input: String): Int {
        return input
            .lines()
            .sumOf { Round.from(it).score() }
    }
}

object Task2 {
    fun totalScore(input: String): Int {
        return input
            .lines()
            .sumOf { ShapeWithOutcome.from(it).toRound().score() }
    }
}

fun String.toShape(): Shape {
    return when (this) {
        "A", "X" -> Shape.Rock
        "B", "Y" -> Shape.Paper
        "C", "Z" -> Shape.Scissors
        else -> throw IllegalArgumentException("Illegal character: $this")
    }
}

fun String.toOutcome(): Outcome {
    return when (this) {
        "X" -> Outcome.Loss
        "Y" -> Outcome.Draw
        "Z" -> Outcome.Win
        else -> throw IllegalArgumentException("Illegal character: $this")
    }
}