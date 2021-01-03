package adventofcode.year2015.day13

typealias Guest = String
typealias Happiness = Int
typealias Seating = Map<Pair<Guest, Guest>, Happiness>

object Task1 {
    fun maxHappiness(input: String): Int {
        val (guests, seating) = getGuestsAndSeatingFromInput(input)
        return maxHappiness(guests, seating)
    }
}

object Task2 {
    fun maxHappiness(input: String): Int {
        val (guests, seating) = getGuestsAndSeatingFromInput(input)

        val newGuests = guests.toMutableSet()
        val newSeating = seating.toMutableMap()

        newGuests += "Me"

        for (guest in guests) {
            newSeating += Pair("Me", guest) to 0
            newSeating += Pair(guest, "Me") to 0
        }

        return maxHappiness(newGuests, newSeating)
    }
}

private fun getGuestsAndSeatingFromInput(input: String): Pair<Set<Guest>, Seating> {
    val guests = mutableSetOf<Guest>()
    val seating = mutableMapOf<Pair<Guest, Guest>, Happiness>()

    for (line in input.replace(".", "").lines()) {
        val parts = line.split(" ")

        guests += parts.first()
        guests += parts.last()

        val happiness = if (parts[2] == "lose") -parts[3].toInt() else parts[3].toInt()

        seating += Pair(parts.first(), parts.last()) to happiness
    }

    return Pair(guests, seating)
}

private fun maxHappiness(guests: Set<Guest>, seating: Seating): Int {
    val arrangements = guests.toList().permutations()
    var maxHappiness = Int.MIN_VALUE

    for (arrangement in arrangements) {
        var happiness = 0
        for (index in arrangement.indices) {
            val nextIndex = if (index == arrangement.size - 1) 0 else index + 1

            val guest1 = arrangement[index]
            val guest2 = arrangement[nextIndex]

            happiness += seating[guest1 to guest2] ?: 0
            happiness += seating[guest2 to guest1] ?: 0
        }

        if (happiness > maxHappiness) {
            maxHappiness = happiness
        }
    }

    return maxHappiness
}

fun <T> List<T>.permutations(): Set<List<T>> {
    if (isEmpty()) return setOf(emptyList())

    val result = mutableSetOf<List<T>>()
    for (i in indices) {
        (this - this[i]).permutations().forEach { item ->
            result.add(item + this[i])
        }
    }
    return result
}