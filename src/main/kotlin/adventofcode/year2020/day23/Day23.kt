package adventofcode.year2020.day23

typealias Cup = Int

object Task1 {
    fun getCupLabels(input: String): String {
        return getCupLabels(input, 100)
    }

    private fun getCupLabels(input: String, iterations: Int): String {
        val cups = input.map { it.toString().toInt() }.toMutableList()
        val cupsSize = cups.size

        var currentCupIndex = 0

        repeat(iterations) {
            val currentCup = cups[currentCupIndex]

            val cupsToRemoveIndex1 = (currentCupIndex + 1) % cups.size
            val cupsToRemoveIndex2 = (currentCupIndex + 2) % cups.size
            val cupsToRemoveIndex3 = (currentCupIndex + 3) % cups.size

            val cupsToRemove = listOf(cups[cupsToRemoveIndex1], cups[cupsToRemoveIndex2], cups[cupsToRemoveIndex3])
            cups.removeAll(cupsToRemove)

            val destinationIndex = findDestinationIndex(cups, currentCup, cupsSize)
            cups.addAll(destinationIndex, cupsToRemove)

            currentCupIndex = (cups.indexOf(currentCup) + 1) % cups.size
        }

        return (cups.subList(cups.indexOf(1) + 1, cups.size) + cups.subList(0, cups.indexOf(1))).joinToString("")
    }

    private fun findDestinationIndex(cups: List<Cup>, currentCup: Cup, cupsSize: Int): Int {
        var destinationValue = getDestinationValue(currentCup - 1, cupsSize)
        var destinationCup = cups.firstOrNull { it == destinationValue }

        while (destinationCup == null) {
            destinationValue = getDestinationValue(destinationValue - 1, cupsSize)
            destinationCup = cups.firstOrNull { it == destinationValue }
        }

        return cups.indexOf(destinationCup) + 1
    }

    private fun getDestinationValue(cup: Cup, cupsSize: Int): Int {
        var destinationValue = Math.floorMod(cup, cupsSize)
        if (destinationValue == 0) {
            destinationValue = cupsSize
        }

        return destinationValue
    }
}