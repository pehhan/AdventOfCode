package adventofcode.year2020.day7

object Task1 {
    fun numberOfBagsThatContainBag(input: String, bag: String): Int {
        val bagList = input
            .replace("bags", "bag")
            .lines()

        return bagsThatContainBag(bagList, bag).size
    }

    private fun bagsThatContainBag(lines: List<String>, bag: String): Set<String> {
        val bagsWithBag = lines.filter { it.secondBagPart().contains(bag) }
        val bagsWithoutBag = lines - bagsWithBag
        val uniqueBags = bagsWithBag.map { it.firstBagPart() }.toSet()

        return bagsWithBag.fold(uniqueBags) { bags, newBag -> bags + bagsThatContainBag(bagsWithoutBag, newBag.firstBagPart()) }
    }
}

object Task2 {
    fun numberOfBagsThatContainBag(input: String, bag: String): Int {
        val bagList = input
            .replace("bags", "bag")
            .replace(".", "")
            .replace("no", "0")
            .lines()

        return bagsThatContainBag(bagList, bag)
    }

    private fun bagsThatContainBag(lines: List<String>, bag: String): Int {
        val bagsWithBag = lines.filter { it.firstBagPart().contains(bag) }
        val bagsToFind = bagsWithBag.map { it.secondBagPart() }

        if (bagsToFind.isEmpty()) {
            return 0
        } else {
            val bagsCountList = bagsToFind
                .joinToString("")
                .split(", ")
                .map { it.substringAfter(" ") to it.substringBefore(" ").toInt() }

            val currentSum = bagsCountList.map { it.second }.sum()
            return bagsCountList.fold(currentSum) { sum, entry -> sum + entry.second * bagsThatContainBag(lines, entry.first) }
        }
    }
}

fun String.firstBagPart(): String {
    return substringBefore(" contain ")
}

fun String.secondBagPart(): String {
    return substringAfter(" contain ")
}