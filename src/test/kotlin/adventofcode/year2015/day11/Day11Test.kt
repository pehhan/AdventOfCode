package adventofcode.year2015.day11

import adventofcode.FileReader
import org.junit.Test

class Day11Test {

    @Test
    fun testDay11Task1() {
        val input = FileReader.getResource("year2015/day11.txt")
        println("2015 Day 11 Task 1: ${Task1.nextPassword(input)}")
    }

    @Test
    fun testDay11Task2() {
        val input = FileReader.getResource("year2015/day11.txt")
        println("2015 Day 11 Task 2: ${Task1.nextPassword(Task1.nextPassword(input))}")
    }
}