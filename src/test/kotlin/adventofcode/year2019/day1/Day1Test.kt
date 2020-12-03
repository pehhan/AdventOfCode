package adventofcode.year2019.day1

import adventofcode.FileReader
import org.junit.Test

class Day1Test {

    @Test
    fun testDay1Task1() {
        val lines = FileReader.getResource("year2019/day1.txt").lines()
        println("2019 Day 1 Task 1: ${Task1.fuelRequired(lines.map { it.toInt() })}")
    }

    @Test
    fun testDay1Task2() {
        val lines = FileReader.getResource("year2019/day1.txt").lines()
        println("2019 Day 1 Task 2: ${Task2.fuelRequired(lines.map { it.toInt() })}")
    }
}