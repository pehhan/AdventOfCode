package adventofcode.year2015.day17

object Task1 {
    fun numberOfCombinations(input: String): Int {
        return numberOfCombinations(input.lines().map { it.toInt() }, 150, true)
    }

    private fun numberOfCombinations(containers: List<Int>, sum: Int, countSubLists: Boolean): Int {
        if (containers.isEmpty()) return 0
        if (containers.size in 1..2) return if (containers.sum() == sum) 1 else 0

        var combinations = 0

        for (i in 1 until containers.size) {
            val containerSum = containers[0] + containers[i]
            if (containerSum == sum) {
                combinations++
            } else if (containerSum < sum) {
                combinations += numberOfCombinations(listOf(containerSum) + containers.drop(i + 1), sum, false)
            }
        }

        return if (countSubLists) {
            combinations + numberOfCombinations(containers.drop(1), sum, true)
        } else {
            combinations
        }
    }
}

object Task2 {
    fun numberOfCombinations(input: String): Int {
        val numberOfContainers = numberOfContainersForCombination(input.lines().map { it.toInt() }, 150, true, 0).filterNot { it == 0 }
        return numberOfContainers.count { it == numberOfContainers.minOrNull() }
    }

    private fun numberOfContainersForCombination(containers: List<Int>, sum: Int, countSubLists: Boolean, previousNumberOfContainers: Int): List<Int> {
        if (containers.isEmpty()) return listOf(0)
        if (containers.size in 1..2) return if (containers.sum() == sum) listOf(previousNumberOfContainers + 2) else listOf(0)

        val numberOfContainers = mutableListOf<Int>()

        for (i in 1 until containers.size) {
            val containerSum = containers[0] + containers[i]
            if (containerSum == sum) {
                numberOfContainers += previousNumberOfContainers + 2
            } else if (containerSum < sum) {
                numberOfContainers += numberOfContainersForCombination(listOf(containerSum) + containers.drop(i + 1), sum, false, previousNumberOfContainers + 2)
            }
        }

        return if (countSubLists) {
            numberOfContainers + numberOfContainersForCombination(containers.drop(1), sum, true, 0)
        } else {
            numberOfContainers
        }
    }
}