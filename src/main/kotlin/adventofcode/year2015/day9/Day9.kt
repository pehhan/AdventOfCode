package adventofcode.year2015.day9

typealias City = String

data class Route(val from: City, val to: City, val distance: Int) {
    companion object {
        fun from(str: String): Route {
            val (cities, distance) = str.split(" = ")
            val (from, to) = cities.split(" to ")

            return Route(from, to, distance.toInt())
        }
    }
}

object Task1 {
    fun shortestDistance(input: String): Int {
        input
            .lines()
            .map { Route.from(it) }

        return 0
    }
}