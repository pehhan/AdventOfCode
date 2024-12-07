package adventofcode.year2024.day2

import adventofcode.FileReader
import org.junit.Test

class Day2Test {

    @Test
    fun testDay2Task1() {
        val input = FileReader.getResource("year2024/day2.txt")
        println("2024 Day 2 Task 1: ${Task1.safeReports(input)}")
    }

//    @Test
//    fun testDay2Task2() {
//        val input = FileReader.getResource("year2024/day2.txt")
//        println("2024 Day 2 Task 2: ${Task2.similarity(input)}")
//    }
}