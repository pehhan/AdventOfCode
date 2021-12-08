package adventofcode.year2021.day8

import adventofcode.FileReader
import org.junit.Test

class Day8Test {

    @Test
    fun testDay8Task1() {
        val lines = FileReader.getResource("year2021/day8.txt").lines()
        println("2021 Day 8 Task 1: ${Task1.occurrences(lines)}")
    }

    @Test
    fun testDay8Task2() {
        val lines = FileReader.getResource("year2021/day8.txt").lines()
        println("2021 Day 8 Task 2: ${Task2.sumOfOutputValues(lines)}")
    }
}