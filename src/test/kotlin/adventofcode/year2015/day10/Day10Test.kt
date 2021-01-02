package adventofcode.year2015.day10

import adventofcode.FileReader
import org.junit.Test

class Day10Test {

    @Test
    fun testDay10Task1() {
        val input = FileReader.getResource("year2015/day10.txt")
        println("2015 Day 10 Task 1: ${Task.lengthOfResult(input, 40)}")
    }

    @Test
    fun testDay10Task2() {
        val input = FileReader.getResource("year2015/day10.txt")
        println("2015 Day 10 Task 2: ${Task.lengthOfResult(input, 50)}")
    }
}