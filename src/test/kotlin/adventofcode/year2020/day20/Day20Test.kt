package adventofcode.year2020.day20

import adventofcode.FileReader
import org.junit.Test

class Day20Test {

    @Test
    fun testDay20Task1() {
        val input = FileReader.getResource("year2020/day20.txt")
        println("2020 Day 20 Task 1: ${Task1.result(input)}")
    }
}