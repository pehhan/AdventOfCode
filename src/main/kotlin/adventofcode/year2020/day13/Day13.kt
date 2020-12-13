package adventofcode.year2020.day13

data class Bus(val id: Long, val timestamp: Long)

object Task1 {
    fun result(input: String): Long {
        val earliestTimestamp = input.lines()[0].toLong()

        return input
            .lines()[1]
            .split(",")
            .filterNot { it == "x" }
            .map { it.toLong() }
            .map { Bus(it, (earliestTimestamp / it) * it + it) }
            .minByOrNull { it.timestamp }
            .let { if (it != null) { it.id * (it.timestamp - earliestTimestamp) } else { 0 } }
    }
}

object Task2 {
    fun result(input: String): Long {
        val buses = input
            .lines()[1]
            .split(",")
            .mapIndexed { index, str ->
                if (str != "x") {
                    Bus(str.toLong(), index.toLong())
                } else {
                    null
                }
            }
            .filterNotNull()

        return findTimestamp(buses)
    }

    private fun findTimestamp(buses: List<Bus>): Long {
        var timestamp = buses[0].timestamp
        var step = buses[0].id

        for (bus in buses.drop(1)) {
            // Find the first matching timestamp for the n:th bus.
            // To optimize and not have to check every possible timestamp,
            // we only check the timestamps that are actual candidates.
            while ((timestamp + bus.timestamp) % bus.id != 0L) {
                timestamp += step
            }

            // The only new possible timestamps will be at the product of the previous bus id:s.
            step *= bus.id
        }

        return timestamp
    }
}