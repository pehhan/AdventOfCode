package adventofcode.year2023.day2

import adventofcode.FileReader
import org.junit.Test

class Day2Test {

    @Test
    fun testDay2Task1() {
        val input = FileReader.getResource("year2023/day2.txt")
        println("2023 Day 2 Task 1: ${Task1.sum(input)}")
    }

    @Test
    fun testDay2Task2() {
        val input = FileReader.getResource("year2023/day2.txt")
        println("2023 Day 2 Task 2: ${Task2.sum(input)}")
    }
}