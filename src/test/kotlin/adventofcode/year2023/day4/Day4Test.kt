package adventofcode.year2023.day4

import adventofcode.FileReader
import org.junit.Test

class Day4Test {

    @Test
    fun testDay4Task1() {
        val input = FileReader.getResource("year2023/day4.txt")
        println("2023 Day 4 Task 1: ${Task1.totalPoints(input)}")
    }

    @Test
    fun testDay4Task2() {
        val input = FileReader.getResource("year2023/day4.txt")
        println("2023 Day 4 Task 2: ${Task2.totalCards(input)}")
    }
}