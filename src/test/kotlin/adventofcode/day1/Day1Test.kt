package adventofcode.day1

import adventofcode.FileReader
import org.junit.Test

class Day1Test {

    @Test
    fun testDay1Task1() {
        val lines = FileReader.getResource("day1.txt").lines()
        println("Day 1 Task 1: ${Task1.findValue(lines.map { it.toInt() })}")
    }

    @Test
    fun testDay1Task2() {
        val lines = FileReader.getResource("day1.txt").lines()
        println("Day 1 Task 2: ${Task2.findValue(lines.map { it.toInt() })}")
    }
}