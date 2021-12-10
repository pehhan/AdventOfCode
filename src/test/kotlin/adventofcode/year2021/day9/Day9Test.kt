package adventofcode.year2021.day9

import adventofcode.FileReader
import org.junit.Test

class Day9Test {

    @Test
    fun testDay9Task1() {
        val lines = FileReader.getResource("year2021/day9.txt").lines()
        println("2021 Day 9 Task 1: ${Task1.riskLevelSum(lines)}")
    }

    @Test
    fun testDay9Task2() {
        val lines = FileReader.getResource("year2021/day9.txt").lines()
        println("2021 Day 9 Task 2: ${Task2.largestBasinsProduct(lines)}")
    }
}