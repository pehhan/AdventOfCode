package adventofcode.year2019.day3

import adventofcode.FileReader
import org.junit.Test

class Day3Test {

    @Test
    fun testDay3Task1() {
        val input = FileReader.getResource("year2019/day3.txt")
        println("2019 Day 3 Task 1: ${Task1.manhattanDistance(input)}")
    }

    @Test
    fun testDay3Task2() {
        val input = FileReader.getResource("year2019/day3.txt")
        println("2019 Day 3 Task 2: ${Task2.fewestSteps(input)}")
    }
}