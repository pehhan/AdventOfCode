package adventofcode.year2023.day5

enum class Category {
    Seed,
    Soil,
    Fertilizer,
    Water,
    Light,
    Temperature,
    Humidity,
    Location;

    companion object {
        fun from(str: String): Category {
            return when (str) {
                "seed" -> Seed
                "soil" -> Soil
                "fertilizer" -> Fertilizer
                "water" -> Water
                "light" -> Light
                "temperature" -> Temperature
                "humidity" -> Humidity
                "location" -> Location
                else -> throw IllegalArgumentException("Unexpected category: $str")
            }
        }
    }
}

data class CategoryMap(val source: Category, val destination: Category, val ranges: List<CategoryRange>) {

    fun toDestinationNumber(sourceNumber: Long): Long {
        for (range in ranges) {
            if (sourceNumber in range.sourceRange) {
                return range.toDestinationNumber(sourceNumber)
            }
        }

        return sourceNumber
    }
}

data class CategoryRange(val sourceStart: Long, val destinationStart: Long, val length: Long) {

    val sourceRange: LongRange
        get() = sourceStart..sourceStart + length

    fun toDestinationNumber(sourceNumber: Long): Long {
        return if (sourceNumber in sourceRange) {
            destinationStart + sourceNumber - sourceStart
        } else {
            sourceNumber
        }
    }
}

object Task1 {
    fun lowestLocationNumber(input: String): Long {
        val seeds = seeds(input)
        val maps = categoryMaps(input)
        val locationNumbers = convertSeedNumbersToLocationNumbers(seeds, maps)

        return locationNumbers.min()
    }

    fun convertSeedNumbersToLocationNumbers(seeds: List<Long>, maps: List<CategoryMap>): List<Long> {
        var category = Category.Seed
        var numbers = seeds

        while (category != Category.Location) {
            val map = maps.first { it.source == category }
            numbers = numbers.map { map.toDestinationNumber(it) }

            category = map.destination
        }

        return numbers
    }

    private fun seeds(input: String): List<Long> {
        return input
            .lines()
            .first()
            .substringAfter(": ")
            .split(" ")
            .map { it.toLong() }
    }
}

object Task2 {
    fun lowestLocationNumber(input: String): Long {
        val seedRanges = seedRanges(input)

        // This blows up when running with real input, need smarter solution
        val seeds = seedRanges.flatten()
        val maps = categoryMaps(input)

        val locationNumbers = Task1.convertSeedNumbersToLocationNumbers(seeds, maps)

        return locationNumbers.min()
    }

    private fun seedRanges(input: String): List<LongRange> {
        val ranges = input
            .lines()
            .first()
            .substringAfter(": ")
            .split(" ")

        val result = mutableListOf<LongRange>()

        for (i in ranges.indices step 2) {
            val start = ranges[i].toLong()
            val length = ranges[i + 1].toLong()

            result += start..<start + length
        }

        return result
    }
}

private fun categoryMaps(input: String): List<CategoryMap> {
    val result = mutableListOf<CategoryMap>()
    val maps = input.split("\n\n").drop(1)

    for (map in maps) {
        val source = Category.from(map.lines().first().substringBefore("-to"))
        val destination = Category.from(map.lines().first().substringAfter("to-").substringBefore(" "))
        val ranges = ranges(map)

        result += CategoryMap(source, destination, ranges)
    }

    return result
}

private fun ranges(input: String): List<CategoryRange> {
    return input
        .lines()
        .drop(1)
        .map { CategoryRange(it.split(" ")[1].toLong(), it.split(" ")[0].toLong(), it.split(" ")[2].toLong()) }
}