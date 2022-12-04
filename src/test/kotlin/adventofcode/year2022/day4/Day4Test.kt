package adventofcode.year2022.day4

import adventofcode.FileReader
import org.junit.Test

class Day4Test {

    @Test
    fun testDay4Task1() {
        val input = FileReader.getResource("year2022/day4.txt")
        println("2022 Day 4 Task 1: ${Task1.numberOfAssignmentPairs(input)}")
    }

    @Test
    fun testDay4Task2() {
        val input = FileReader.getResource("year2022/day4.txt")
        println("2022 Day 4 Task 2: ${Task2.numberOfAssignmentPairs(input)}")
    }
}