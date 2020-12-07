package adventofcode.year2015.day1

import adventofcode.FileReader
import org.junit.Test

class Day1Test {

    @Test
    fun testDay1Task1() {
        val input = FileReader.getResource("year2015/day1.txt")
        println("2015 Day 1 Task 1: ${Task1.findFloor(input)}")
    }

    @Test
    fun testDay1Task2() {
        val input = FileReader.getResource("year2015/day1.txt")
        println("2015 Day 1 Task 2: ${Task2.findPosition(input)}")
    }
}