package adventofcode.year2020.day10

import adventofcode.FileReader
import org.junit.Test

class Day10Test {

    @Test
    fun testDay10Task1() {
        val input = FileReader.getResource("year2020/day10.txt")
        println("2020 Day 10 Task 1: ${Task1.result(input)}")
    }

    @Test
    fun testDay10Task2() {
        val input = FileReader.getResource("year2020/day10.txt")
        println("2020 Day 10 Task 2: ${Task2.result(input)}")
    }
}