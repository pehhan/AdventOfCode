package adventofcode.year2019.day5

import adventofcode.FileReader
import org.junit.Test

class Day5Test {

    @Test
    fun testDay5Task1() {
        val input = FileReader.getResource("year2019/day5.txt")
        Task1.runProgram(input.split(",").map { it.toInt() }, 1)
    }
}