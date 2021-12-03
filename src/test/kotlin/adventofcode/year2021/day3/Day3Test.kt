package adventofcode.year2021.day3

import adventofcode.FileReader
import org.junit.Test

class Day3Test {

    @Test
    fun testDay3Task1() {
        val lines = FileReader.getResource("year2021/day3.txt").lines()
        println("2021 Day 3 Task 1: ${Task1.powerConsumption(lines)}")
    }

    @Test
    fun testDay3Task2() {
        val lines = FileReader.getResource("year2021/day3.txt").lines()
        println("2021 Day 3 Task 2: ${Task2.lifeSupportRating(lines)}")
    }
}