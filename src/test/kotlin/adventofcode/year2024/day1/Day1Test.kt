package adventofcode.year2024.day1

import adventofcode.FileReader
import org.junit.Test

class Day1Test {

    @Test
    fun testDay1Task1() {
        val input = FileReader.getResource("year2024/day1.txt")
        println("2024 Day 1 Task 1: ${Task1.distance(input)}")
    }
}