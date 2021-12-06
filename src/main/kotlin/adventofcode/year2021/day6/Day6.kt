package adventofcode.year2021.day6

fun numberOfFish(input: String, days: Int): Long {
    var fishMap = mutableMapOf<Int, Long>()

    for (fish in input.split(",").map { it.toInt() }) {
        fishMap[fish] = (fishMap[fish] ?: 0) + 1
    }

    repeat(days) {
        val newborns = fishMap[0] ?: 0
        val newFishMap = mutableMapOf<Int, Long>()

        fishMap.forEach {
            if (it.key == 0) {
                newFishMap[6] = (newFishMap[6] ?: 0) + it.value
            } else {
                newFishMap[it.key - 1] = (newFishMap[it.key - 1] ?: 0) + it.value
            }
        }

        newFishMap[8] = newborns
        fishMap = newFishMap
    }

    return fishMap.values.sum()
}