package adventofcode.year2023.day2

import java.lang.IllegalArgumentException

enum class Cube {
    Red, Green, Blue
}

data class RevealedCubes(val cube: Cube, val number: Int)

data class Game(val id: Int, val revealedCubes: List<Set<RevealedCubes>>) {

    fun isPossible(cube: Cube, max: Int): Boolean {
        return !revealedCubes.flatten().any { it.cube == cube && it.number > max }
    }
}

fun List<Game>.filterMaxCubes(cube: Cube, max: Int): List<Game> {
    return filter { it.isPossible(cube, max) }
}

object Task1 {
    fun sum(input: String): Int {
        return input
            .lines()
            .map { it.toGame() }
            .filterMaxCubes(Cube.Red, 12)
            .filterMaxCubes(Cube.Green, 13)
            .filterMaxCubes(Cube.Blue, 14)
            .sumOf { it.id }
    }
}

private fun String.toGame(): Game {
    val id = substringBefore(":").substringAfter("Game ").toInt()
    val sets = substringAfter(": ")
        .split("; ")
        .map { it.toRevealedCubesSet() }

    return Game(id, sets)
}

private fun String.toRevealedCubesSet(): Set<RevealedCubes> {
    return split(", ").map { RevealedCubes(it.split(" ").last().toCube(), it.split(" ").first().toInt()) }.toSet()
}

private fun String.toCube(): Cube {
    return when (this) {
        "red" -> Cube.Red
        "green" -> Cube.Green
        "blue" -> Cube.Blue
        else -> throw IllegalArgumentException("Incorrect cube: $this")
    }
}