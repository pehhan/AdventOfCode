package adventofcode.year2023.day15

typealias Box = Int
typealias FocalLength = Int
typealias Label = String
typealias LensMap = Map<Box, List<Lens>>

data class Lens(val label: Label, val focalLength: FocalLength)

object Task1 {
    fun result(input: String): Int {
        return input.split(",").sumOf { it.hash() }
    }
}

object Task2 {
    fun result(input: String): Int {
        val lensMap = input.split(",").fold(mapOf<Box, List<Lens>>()) { acc, str ->
            if (str.contains("=")) {
                insertLensIntoBox(str, acc)
            } else {
                removeLensFromBox(str, acc)
            }
        }

        return lensMap.focusingPower()
    }

    private fun insertLensIntoBox(str: String, map: LensMap): LensMap {
        val newLensMap = map.toMutableMap()

        val (label, focalLength) = str.split("=")
        val newLens = Lens(label, focalLength.toInt())
        val box = label.hash()
        val lenses = map[box] ?: emptyList()
        val existingLens = lenses.firstOrNull { it.label == label }

        if (existingLens != null) {
            val newLenses = lenses.replace(existingLens, newLens)
            newLensMap[box] = newLenses
        } else {
            newLensMap[box] = lenses + newLens
        }

        return newLensMap
    }

    private fun removeLensFromBox(str: String, map: LensMap): LensMap {
        val newLensMap = map.toMutableMap()

        val label = str.substringBefore("-")
        val box = label.hash()
        val lenses = map[box] ?: emptyList()

        val newLenses = lenses.toMutableList()
        newLenses.removeIf { it.label == label }

        newLensMap[box] = newLenses

        return newLensMap
    }

    private fun LensMap.focusingPower(): Int {
        return map { it.focusingPower() }.sum()
    }

    private fun Map.Entry<Box, List<Lens>>.focusingPower(): Int {
        return value.mapIndexed { index, lens ->
            (key + 1) * (index + 1) * lens.focalLength
        }.sum()
    }

    private fun <T> Iterable<T>.replace(old: T, new: T) = map { if (it == old) new else it }
}

private fun String.hash(): Int {
    return fold(0) { acc, char ->
        ((acc + char.code) * 17) % 256
    }
}