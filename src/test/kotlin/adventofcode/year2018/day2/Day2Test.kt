package adventofcode.year2018.day2

import adventofcode.FileReader
import org.junit.Test

class Day2Test {

    @Test
    fun testDay2Task1() {
        val input = FileReader.getResource("year2018/day2.txt")
        println("2018 Day 2 Task 1: ${Task1.checksum(input)}")
    }
}