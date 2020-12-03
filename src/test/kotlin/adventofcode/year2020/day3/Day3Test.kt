package adventofcode.year2020.day3

import adventofcode.FileReader
import org.junit.Test

class Day3Test {

    @Test
    fun testDay3Task1() {
        val lines = FileReader.getResource("year2020/day3.txt").lines()
        println("2020 Day 3 Task 1: ${Task1.numberOfTrees(lines)}")
    }

    @Test
    fun testDay3Task2() {
        val lines = FileReader.getResource("year2020/day3.txt").lines()
        println("2020 Day 3 Task 2: ${Task2.numberOfTrees(lines)}")
    }
}