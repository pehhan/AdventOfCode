package adventofcode.year2021.day13

import adventofcode.FileReader
import org.junit.Test

class Day13Test {

    @Test
    fun testDay13Task1() {
        val input = FileReader.getResource("year2021/day13.txt")
        println("2021 Day 13 Task 1: ${Task1.visibleDots(input)}")
    }

    @Test
    fun testDay13Task2() {
        val input = FileReader.getResource("year2021/day13.txt")
        println("2021 Day 13 Task 2:\n${Task2.code(input)}")
    }
}