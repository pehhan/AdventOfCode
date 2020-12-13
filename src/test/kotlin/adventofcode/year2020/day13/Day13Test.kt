package adventofcode.year2020.day13

import adventofcode.FileReader
import org.junit.Test

class Day13Test {

    @Test
    fun testDay13Task1() {
        val input = FileReader.getResource("year2020/day13.txt")
        println("2020 Day 13 Task 1: ${Task1.result(input)}")
    }

    @Test
    fun testDay13Task2() {
        val input = FileReader.getResource("year2020/day13.txt")
        println("2020 Day 13 Task 2: ${Task2.result(input)}")
    }
}