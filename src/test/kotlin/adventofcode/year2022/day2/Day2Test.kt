package adventofcode.year2022.day2

import adventofcode.FileReader
import org.junit.Test

class Day2Test {

    @Test
    fun testDay2Task1() {
        val input = FileReader.getResource("year2022/day2.txt")
        println("2022 Day 1 Task 1: ${Task1.totalScore(input)}")
    }

    @Test
    fun testDay2Task2() {
        val input = FileReader.getResource("year2022/day2.txt")
        println("2022 Day 2 Task 2: ${Task2.totalScore(input)}")
    }
}