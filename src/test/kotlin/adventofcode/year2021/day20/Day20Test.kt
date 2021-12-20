package adventofcode.year2021.day20

import adventofcode.FileReader
import org.junit.Test

class Day20Test {

    @Test
    fun testDay20Task1() {
        val input = FileReader.getResource("year2021/day20.txt")
        println("2021 Day 20 Task 1: ${Task1.numberOfLitPixels(input)}")
    }

    @Test
    fun testDay20Task2() {
        val input = FileReader.getResource("year2021/day20.txt")
        println("2021 Day 20 Task 2: ${Task2.numberOfLitPixels(input)}")
    }
}