package adventofcode.year2015.day9

import adventofcode.FileReader
import org.junit.Test

class Day9Test {

    @Test
    fun testDay9Task1() {
        val input = FileReader.getResource("year2015/day9.txt")
        println("2015 Day 9 Task 1: ${Task1.shortestDistance(input)}")
    }
}