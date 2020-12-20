package adventofcode.year2020.day19

import adventofcode.FileReader
import org.junit.Test

class Day19Test {

    @Test
    fun testDay19Task1() {
        val input = FileReader.getResource("year2020/day19.txt")
        println("2020 Day 19 Task 1: ${Task1.numberOfMatchingMessages(input)}")
    }

    @Test
    fun testDay19Task2() {
        val input = FileReader.getResource("year2020/day19.txt")
        println("2020 Day 19 Task 2: ${Task2.numberOfMatchingMessages(input)}")
    }
}