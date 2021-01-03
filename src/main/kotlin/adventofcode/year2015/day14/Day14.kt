package adventofcode.year2015.day14

data class Reindeer(val name: String, val speed: Int, val flyDuration: Int, val restDuration: Int, var points: Int = 0) {

    fun distanceAfter(seconds: Int): Int {
        var i = 0
        var distance = 0
        while (i < seconds) {
            val actualFlyDuration = if (i + flyDuration >= seconds) seconds - i else flyDuration

            distance += speed * actualFlyDuration
            i += actualFlyDuration + restDuration
        }

        return distance
    }

    companion object {
        fun from(line: String): Reindeer {
            val parts = line.split(" ")
            val name = parts[0]
            val speed = parts[3].toInt()
            val flyDuration = parts[6].toInt()
            val restDuration = parts[13].toInt()

            return Reindeer(name, speed, flyDuration, restDuration)
        }
    }
}

object Task1 {
    fun maxDistance(input: String): Int {
        return input
            .lines()
            .map { Reindeer.from(it) }
            .map { it.distanceAfter(2503) }
            .maxOrNull() ?: 0
    }
}

object Task2 {
    fun winningPoints(input: String): Int {
        val reindeer = input.lines().map { Reindeer.from(it) }

        for (second in 1..2503) {
            val distances = reindeer.map { it.distanceAfter(second) }
            var max = Int.MIN_VALUE
            val maxIndices = mutableListOf<Int>()

            for (i in distances.indices) {
                val distance = distances[i]
                if (distance > max) {
                    max = distance
                    maxIndices.clear()
                    maxIndices += i
                } else if (distance == max) {
                    maxIndices += i
                }
            }

            for (index in maxIndices) {
                reindeer[index].points++
            }
        }

        return reindeer.maxOf { it.points }
    }
}