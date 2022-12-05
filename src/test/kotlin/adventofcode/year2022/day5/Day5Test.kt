package adventofcode.year2022.day5

import adventofcode.FileReader
import org.junit.Test

class Day5Test {

    @Test
    fun testDay5Task1() {
        val input = FileReader.getResource("year2022/day5.txt")
        println("2022 Day 5 Task 1: ${Task1.cratesOnTop(input)}")
    }

    @Test
    fun testDay5Task2() {
        val input = FileReader.getResource("year2022/day5.txt")
        println("2022 Day 5 Task 2: ${Task2.cratesOnTop(input)}")
    }
}