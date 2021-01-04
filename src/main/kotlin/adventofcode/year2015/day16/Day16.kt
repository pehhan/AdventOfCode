package adventofcode.year2015.day16

data class AuntSue(val number: Int, val compounds: Map<String, Int>) {

    private val children: Int?
        get() = compounds["children"]

    private val cats: Int?
        get() = compounds["cats"]

    private val samoyeds: Int?
        get() = compounds["samoyeds"]

    private val pomeranians: Int?
        get() = compounds["pomeranians"]

    private val akitas: Int?
        get() = compounds["akitas"]

    private val vizslas: Int?
        get() = compounds["vizslas"]

    private val goldfish: Int?
        get() = compounds["goldfish"]

    private val trees: Int?
        get() = compounds["trees"]

    private val cars: Int?
        get() = compounds["cars"]

    private val perfumes: Int?
        get() = compounds["perfumes"]

    fun matchesPart1(): Boolean {
        if (children != null && children != 3) return false
        if (cats != null && cats != 7) return false
        if (samoyeds != null && samoyeds != 2) return false
        if (pomeranians != null && pomeranians != 3) return false
        if (akitas != null && akitas != 0) return false
        if (vizslas != null && vizslas != 0) return false
        if (goldfish != null && goldfish != 5) return false
        if (trees != null && trees != 3) return false
        if (cars != null && cars != 2) return false
        if (perfumes != null && perfumes != 1) return false

        return true
    }

    fun matchesPart2(): Boolean {
        if (children != null && children != 3) return false
        if (cats != null && cats!! <= 7) return false
        if (samoyeds != null && samoyeds != 2) return false
        if (pomeranians != null && pomeranians!! >= 3) return false
        if (akitas != null && akitas != 0) return false
        if (vizslas != null && vizslas != 0) return false
        if (goldfish != null && goldfish!! >= 5) return false
        if (trees != null && trees!! <= 3) return false
        if (cars != null && cars != 2) return false
        if (perfumes != null && perfumes != 1) return false

        return true
    }

    companion object {
        fun from(line: String): AuntSue {
            val number = line.split(": ")[0].split(" ")[1].toInt()
            val compounds = line.substringAfter(": ").split(", ").map { it.split(": ")[0] to it.split(": ")[1].toInt() }.toMap()

            return AuntSue(number, compounds)
        }
    }
}

object Task1 {
    fun whichAuntSue(input: String): Int {
        val aunts = input.lines().map { AuntSue.from(it) }
        return aunts.filter { it.matchesPart1() }.map { it.number }.first()
    }
}

object Task2 {
    fun whichAuntSue(input: String): Int {
        val aunts = input.lines().map { AuntSue.from(it) }
        return aunts.filter { it.matchesPart2() }.map { it.number }.first()
    }
}