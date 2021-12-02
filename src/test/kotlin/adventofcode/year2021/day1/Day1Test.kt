package adventofcode.year2021.day1

import adventofcode.FileReader
import org.junit.Test

class Day1Test {

    @Test
    fun testDay1Task1() {
        val lines = FileReader.getResource("year2021/day1.txt").lines()
        println("2021 Day 1 Task 1: ${Task1.numberOfIncreases(lines.map { it.toInt() })}")
    }

    @Test
    fun testDay1Task2() {
        val lines = FileReader.getResource("year2021/day1.txt").lines()
        println("2021 Day 1 Task 2: ${Task2.numberOfIncreases(lines.map { it.toInt() })}")
    }
}