package adventofcode.year2021.day6

fun numberOfFish(input: String, days: Int): Long {
    var fishMap = input
        .split(",")
        .map { it.toInt() }
        .groupBy { it }
        .mapValues { it.value.size.toLong() }

    repeat(days) {
        fishMap = tick(fishMap)
    }

    return fishMap.values.sum()
}

private fun tick(map: Map<Int, Long>): Map<Int, Long> {
    val newMap = mutableMapOf<Int, Long>()

    map.forEach {
        if (it.key == 0) {
            newMap[6] = (newMap[6] ?: 0) + it.value
        } else {
            newMap[it.key - 1] = (newMap[it.key - 1] ?: 0) + it.value
        }
    }

    newMap[8] = map[0] ?: 0

    return newMap
}