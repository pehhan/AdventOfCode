package adventofcode.year2021.day14

import adventofcode.FileReader
import org.junit.Test

class Day14Test {

    @Test
    fun testDay14Task1() {
        val input = FileReader.getResource("year2021/day14.txt")
        println("2021 Day 14 Task 1: ${result(input, 10)}")
    }

    @Test
    fun testDay14Task2() {
        val input = FileReader.getResource("year2021/day14.txt")
        println("2021 Day 14 Task 2: ${result(input, 40)}")
    }
}