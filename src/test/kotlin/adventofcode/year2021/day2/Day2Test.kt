package adventofcode.year2021.day2

import adventofcode.FileReader
import org.junit.Test

class Day2Test {

    @Test
    fun testDay2Task1() {
        val lines = FileReader.getResource("year2021/day2.txt").lines()
        println("2021 Day 2 Task 1: ${Task1.getValue(lines)}")
    }

    @Test
    fun testDay2Task2() {
        val lines = FileReader.getResource("year2021/day2.txt").lines()
        println("2021 Day 2 Task 2: ${Task2.getValue(lines)}")
    }
}