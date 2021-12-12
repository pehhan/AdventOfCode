package adventofcode.year2021.day12

data class Cave(private val name: String) {

    val isStart: Boolean
        get() = name == "start"

    val isEnd: Boolean
        get() = name == "end"

    val isSmall: Boolean
        get() = name.all { it.isLowerCase() }
}

object Task1 {
    fun numberOfPaths(lines: List<String>): Int {
        val connections = parseConnections(lines)
        return numberOfPaths(Cave("start"), false, connections, mutableSetOf(), false, 0)
    }
}

object Task2 {
    fun numberOfPaths(lines: List<String>): Int {
        val connections = parseConnections(lines)
        return numberOfPaths(Cave("start"), true, connections, mutableSetOf(), false, 0)
    }
}

private fun parseConnections(lines: List<String>): Map<Cave, Set<Cave>> {
    val connections = HashMap<Cave, MutableSet<Cave>>()

    for (line in lines) {
        val (from, to) = Cave(line.substringBefore("-")) to Cave(line.substringAfter("-"))
        connections.getOrPut(from) { mutableSetOf() } += to
        connections.getOrPut(to) { mutableSetOf() } += from
    }

    return connections
}

private fun numberOfPaths(from: Cave, canVisitSmallCaveTwice: Boolean, connections: Map<Cave, Set<Cave>>, visited: MutableSet<Cave>, visitedBefore: Boolean, prevNumberOfPaths: Int): Int {
    if (from.isEnd) return prevNumberOfPaths + 1

    var numberOfPaths = prevNumberOfPaths

    for (to in connections[from]!!) {
        if (to.isStart) continue

        var visitedNow = visitedBefore

        if (to.isSmall) {
            if (to in visited) {
                if (!canVisitSmallCaveTwice || visitedBefore) continue
                visitedNow = true
            } else {
                visited += to
            }
        }

        numberOfPaths = numberOfPaths(to, canVisitSmallCaveTwice, connections, visited, visitedNow, numberOfPaths)

        if (to.isSmall && visitedBefore == visitedNow) {
            visited -= to
        }
    }

    return numberOfPaths
}