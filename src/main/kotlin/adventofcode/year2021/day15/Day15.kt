package adventofcode.year2021.day15

import java.lang.IllegalArgumentException
import kotlin.math.abs

data class Node(val x: Int, val y: Int, val cost: Int) {

    fun neighbors(allNodes: List<List<Node>>): Set<Node> {
        val neighbors = mutableSetOf<Node>()

        if (x > 0) {
            neighbors += allNodes[y][x - 1]
        }
        if (x < allNodes[0].size - 1) {
            neighbors += allNodes[y][x + 1]
        }
        if (y > 0) {
            neighbors += allNodes[y - 1][x]
        }
        if (y < allNodes.size - 1) {
            neighbors += allNodes[y + 1][x]
        }

        return neighbors
    }
}

object Task1 {
    fun lowestRisk(input: String): Int {
        val cavern = input.lines().mapIndexed { y, line -> line.toList().mapIndexed { x, value -> Node(x, y, value.digitToInt()) } }
        val start = cavern.first().first()
        val end = cavern.last().last()
        val path = findPath(start, end, cavern)

        return path.drop(1).sumOf { it.cost }
    }
}

object Task2 {
    fun lowestRisk(input: String): Int {
        val cavern = input.lines().mapIndexed { y, line -> line.toList().mapIndexed { x, value -> Node(x, y, value.digitToInt()) } }
        val biggerCavern = constructBiggerCavern(cavern)
        val start = biggerCavern.first().first()
        val end = biggerCavern.last().last()
        val path = findPath(start, end, biggerCavern)

        return path.drop(1).sumOf { it.cost }
    }

    private fun constructBiggerCavern(cavern: List<List<Node>>): List<List<Node>> {
        val ySize = cavern.size
        val xSize = cavern[0].size

        val bigCavern = cavern.toMutableList()

        // Repeat y
        repeat(4) { i ->
            val nodesToAdd = cavern.map { list -> list.map { node -> Node(node.x, node.y + ySize * (i + 1), ((node.cost + i) % 9) + 1) } }
            bigCavern += nodesToAdd
        }

        val biggerCavern = bigCavern.toMutableList()

        // Repeat x
        for ((y, line) in bigCavern.withIndex()) {
            repeat(4) { i ->
                val nodesToAdd = line.map { node -> Node(node.x + xSize * (i + 1), node.y, ((node.cost + i) % 9) + 1) }
                biggerCavern[y] += nodesToAdd
            }
        }

        return biggerCavern
    }
}

// A* search
private fun findPath(start: Node, end: Node, allNodes: List<List<Node>>): List<Node> {
    val openSet = mutableSetOf<Node>()
    openSet += start

    val cameFrom = mutableMapOf<Node, Node>()

    val gScore = mutableMapOf<Node, Int>()
    gScore[start] = 0

    val fScore = mutableMapOf<Node, Int>()
    fScore[start] = 0

    while (openSet.isNotEmpty()) {
        val current = openSet.minByOrNull { fScore[it] ?: Int.MAX_VALUE }!!

        if (current == end) {
            return reconstructPath(cameFrom, current)
        }

        openSet -= current

        for (neighbor in current.neighbors(allNodes)) {
            val tentativeGScore = gScore[current]!! + neighbor.cost

            if (tentativeGScore < (gScore[neighbor] ?: Int.MAX_VALUE)) {
                cameFrom[neighbor] = current
                gScore[neighbor] = tentativeGScore
                fScore[neighbor] = tentativeGScore + estimatedCostToEnd(neighbor, allNodes.last().last())

                if (neighbor !in openSet) {
                    openSet += neighbor
                }
            }
        }
    }

    throw IllegalArgumentException("Could not find path")
}

private fun estimatedCostToEnd(from: Node, to: Node): Int {
    return abs(to.x - from.x) + abs(to.y - from.y)
}

private fun reconstructPath(cameFrom: Map<Node, Node>, current: Node): List<Node> {
    val path = mutableListOf(current)
    var node = current
    while (node in cameFrom.keys) {
        node = cameFrom[node]!!
        path += node
    }

    return path.reversed()
}