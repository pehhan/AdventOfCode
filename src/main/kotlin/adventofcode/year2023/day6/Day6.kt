package adventofcode.year2023.day6

data class Race(val time: Int, val recordDistance: Int) {

    fun numberOfWaysToBeatRecord(): Int {
        var beats = 0

        for (i in 1..<time) {
            val speed = i
            val duration = time - speed
            val distance = speed * duration
            if (distance > recordDistance) {
                beats++
            }
        }

        return beats
    }
}

object Task1 {
    fun result(input: String): Int {
        return input
            .toRaces()
            .map { it.numberOfWaysToBeatRecord() }
            .fold(1) { product, ways ->
                product * ways
            }
    }
}

private fun String.toRaces(): List<Race> {
    val times = this
        .lines()
        .first()
        .split("\\s+".toRegex())
        .drop(1)
        .map { it.toInt() }

    val distances = this
        .lines()
        .last()
        .split("\\s+".toRegex())
        .drop(1)
        .map { it.toInt() }

    return times
        .zip(distances)
        .map { Race(it.first, it.second) }
}