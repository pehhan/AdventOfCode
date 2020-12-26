package adventofcode.year2020.day23

import java.lang.IllegalArgumentException

typealias Cup = Int
typealias Cups = Map<Cup, Cup>

object Task1 {
    fun getCupLabels(input: String): String {
        val inputCups = input.map { it.toString().toInt() }
        val cups = getCups(createInitialCupsMap(inputCups), inputCups[0], 100)
        return getStringRepresentation(cups)
    }

    private fun createInitialCupsMap(inputCups: List<Int>): Cups {
        val cups = mutableMapOf<Int, Int>()

        for (i in 0 until inputCups.size - 1) {
            cups[inputCups[i]] = inputCups[i + 1]
        }

        // Fix last item
        cups[inputCups[inputCups.size - 1]] = inputCups[0]

        return cups
    }

    private fun getStringRepresentation(cups: Cups): String {
        var current = cups[1]
        var str = ""
        repeat(cups.size - 1) {
            str += current
            current = cups[current]
        }

        return str
    }
}

object Task2 {
    fun getResult(input: String): Long {
        val inputCups = input.map { it.toString().toInt() }
        val cups = getCups(createInitialCupsMap(inputCups), inputCups[0], 10_000_000)
        return (cups[1]?.toLong() ?: 0) * (cups[cups[1]]?.toLong() ?: 0)
    }

    private fun createInitialCupsMap(inputCups: List<Int>): Cups {
        val cups = mutableMapOf<Int, Int>()

        for (i in 0 until inputCups.size - 1) {
            cups[inputCups[i]] = inputCups[i + 1]
        }

        // Fix last item in input
        cups[inputCups[inputCups.size - 1]] = inputCups.size + 1

        for (i in inputCups.size + 1 until 1_000_000) {
            cups[i] = i + 1
        }

        // Fix last item
        cups[1_000_000] = inputCups[0]

        return cups
    }
}

private fun getCups(cups: Cups, startCup: Cup, iterations: Int): Cups {
    val cupMap = cups.toMutableMap()
    var currentCup = startCup

    repeat(iterations) {
        var value = currentCup

        val cupToRemove1 = cupMap[value] ?: throw IllegalArgumentException("Could not find cup at $value")
        val cupToRemove2 = cupMap[cupToRemove1] ?: throw IllegalArgumentException("Could not find cup at $cupToRemove1")
        val cupToRemove3 = cupMap[cupToRemove2] ?: throw IllegalArgumentException("Could not find cup at $cupToRemove2")

        do {
            value--
            if (value == 0) {
                value = cupMap.size
            }
        } while (value == cupToRemove1 || value == cupToRemove2 || value == cupToRemove3)

        cupMap[currentCup] = cupMap[cupToRemove3] ?: throw IllegalArgumentException("Could not find cup at $cupToRemove3")
        cupMap[cupToRemove3] = cupMap[value] ?: throw IllegalArgumentException("Could not find cup at $value")
        cupMap[value] = cupToRemove1

        currentCup = cupMap[currentCup] ?: throw IllegalArgumentException("Could not find cup at $currentCup")
    }

    return cupMap
}