package adventofcode.year2019.day2

import adventofcode.FileReader
import org.junit.Test

class Day2Test {

    @Test
    fun testDay2Task1() {
        val input = FileReader.getResource("year2019/day2.txt")
        println("2019 Day 2 Task 1: ${Task1.valueAtPosition0(input.split(",").map { it.toInt() })}")
    }

    @Test
    fun testDay2Task2() {
        val input = FileReader.getResource("year2019/day2.txt")
        println("2019 Day 2 Task 2: ${Task2.findValue(input.split(",").map { it.toInt() })}")
    }
}