package adventofcode.year2018.day1

import adventofcode.FileReader
import org.junit.Test

class Day1Test {

    @Test
    fun testDay1Task1() {
        val input = FileReader.getResource("year2018/day1.txt")
        println("2018 Day 1 Task 1: ${Task1.resultingFrequency(input)}")
    }
}