package adventofcode.year2015.day9

import org.jgrapht.Graph
import org.jgrapht.graph.SimpleDirectedGraph

object Task1 {
    fun shortestDistance(input: String): Int {
        val route = toGraph(input).findShortestRoute()
        return route?.distance() ?: 0
    }
}

object Task2 {
    fun longestDistance(input: String): Int {
        val route = toGraph(input).findLongestRoute()
        return route?.distance() ?: 0
    }
}

private fun toGraph(input: String): SimpleDirectedGraph<String, Path> {
    val graph = SimpleDirectedGraph<String, Path>(Path::class.java)

    for (line in input.lines()) {
        val (route, distance) = line.split(" = ")
        val (from, to) = route.split(" to ")

        graph += from
        graph += to
        graph += Path(from, to, distance.toInt())
        graph += Path(to, from, distance.toInt())
    }

    return graph
}

data class Path(val from: String, val to: String, val distance: Int)

fun Iterable<Path>.distance(): Int {
    return sumBy { it.distance }
}

fun Iterable<Path>.visited(): List<String> {
    return map { it.from } + last().to
}

operator fun <V> Graph<V, *>.plusAssign(vertex: V) {
    addVertex(vertex)
}

operator fun Graph<String, Path>.plusAssign(path: Path) {
    addEdge(path.from, path.to, path)
}

fun SimpleDirectedGraph<String, Path>.findShortestRoute(route: List<Path>? = null): List<Path>? {
    val routes = when {
        route == null -> {
            edgeSet().map { findShortestRoute(listOf(it)) }
        }
        route.visited().toSet() == vertexSet() -> {
            listOf(route)
        }
        else -> {
            outgoingEdgesOf(route.last().to)
                .filterNot { route.visited().contains(it.to) }
                .map { findShortestRoute(route + it) }
        }
    }

    return routes.filterNotNull().minByOrNull { it.distance() }
}

fun SimpleDirectedGraph<String, Path>.findLongestRoute(route: List<Path>? = null): List<Path>? {
    val routes = when {
        route == null -> {
            edgeSet().map { findLongestRoute(listOf(it)) }
        }
        route.visited().toSet() == vertexSet() -> {
            listOf(route)
        }
        else -> {
            outgoingEdgesOf(route.last().to)
                .filterNot { route.visited().contains(it.to) }
                .map { findLongestRoute(route + it) }
        }
    }

    return routes.filterNotNull().maxByOrNull { it.distance() }
}
