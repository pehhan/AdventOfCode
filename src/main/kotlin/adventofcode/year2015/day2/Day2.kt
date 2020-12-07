package adventofcode.year2015.day2

data class Present(val l: Int, val w: Int, val h: Int) {

    constructor(str: String): this(str.split("x")[0].toInt(), str.split("x")[1].toInt(), str.split("x")[2].toInt())

    fun area(): Int {
        return 2 * l * w + 2 * w * h + 2 * h * l + minOf(l * w, w * h, h * l)
    }

    fun ribbon(): Int {
        return ribbonForWrap() + ribbonForBow()
    }

    private fun ribbonForWrap(): Int {
        val sortedDimensions = listOf(l, w, h).sorted()
        return sortedDimensions[0] * 2 + sortedDimensions[1] * 2
    }

    private fun ribbonForBow(): Int {
        return l * w * h
    }
}

object Task1 {
    fun totalArea(input: String): Int {
        return input
            .lines()
            .sumOf { Present(it).area() }
    }
}

object Task2 {
    fun totalRibbon(input: String): Int {
        return input
            .lines()
            .sumOf { Present(it).ribbon() }
    }
}