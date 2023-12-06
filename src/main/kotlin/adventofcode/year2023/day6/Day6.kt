package adventofcode.year2023.day6

data class Race(val time: Long, val recordDistance: Long) {

    fun numberOfWaysToBeatRecord(): Long {
        return (1..<time).fold(0) { sum, t ->
            val duration = time - t
            val distance = t * duration
            if (distance > recordDistance) {
                sum + 1
            } else {
                sum
            }
        }
    }
}

object Task1 {
    fun result(input: String): Long {
        return input
            .toRaces()
            .map { it.numberOfWaysToBeatRecord() }
            .fold(1L) { product, ways ->
                product * ways
            }
    }
}

object Task2 {
    fun result(input: String): Long {
        return input.toOneRace().numberOfWaysToBeatRecord()
    }
}

private fun String.toRaces(): List<Race> {
    val times = this
        .lines()
        .first()
        .toLongList()

    val distances = this
        .lines()
        .last()
        .toLongList()

    return times
        .zip(distances)
        .map { Race(it.first, it.second) }
}

private fun String.toLongList(): List<Long> {
    return this
        .split("\\s+".toRegex())
        .drop(1)
        .map { it.toLong() }
}

private fun String.toOneRace(): Race {
    val time = this
        .lines()
        .first()
        .toOneLong()

    val distance = this
        .lines()
        .last()
        .toOneLong()

    return Race(time, distance)
}

private fun String.toOneLong(): Long {
    return this
        .split("\\s+".toRegex())
        .drop(1)
        .joinToString("")
        .toLong()
}