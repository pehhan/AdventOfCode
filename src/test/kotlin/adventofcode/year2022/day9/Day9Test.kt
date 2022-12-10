package adventofcode.year2022.day9

import adventofcode.FileReader
import org.junit.Test

class Day9Test {

    @Test
    fun testDay9Task1() {
        val input = FileReader.getResource("year2022/day9.txt")
        println("2022 Day 9 Task 1: ${Task1.visited(input)}")
    }

    @Test
    fun testDay9Task2() {
        val input = FileReader.getResource("year2022/day9.txt")
        println("2022 Day 9 Task 2: ${Task2.visited(input)}")
    }
}