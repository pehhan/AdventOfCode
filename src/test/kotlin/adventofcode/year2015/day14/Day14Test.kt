package adventofcode.year2015.day14

import adventofcode.FileReader
import org.junit.Test

class Day14Test {

    @Test
    fun testDay14Task1() {
        val input = FileReader.getResource("year2015/day14.txt")
        println("2015 Day 14 Task 1: ${Task1.maxDistance(input)}")
    }

    @Test
    fun testDay14Task2() {
        val input = FileReader.getResource("year2015/day14.txt")
        println("2015 Day 14 Task 2: ${Task2.winningPoints(input)}")
    }
}