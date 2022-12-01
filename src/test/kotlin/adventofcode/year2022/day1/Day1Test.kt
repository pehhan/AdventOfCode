package adventofcode.year2022.day1

import adventofcode.FileReader
import org.junit.Test

class Day1Test {

    @Test
    fun testDay1Task1() {
        val input = FileReader.getResource("year2022/day1.txt")
        println("2022 Day 1 Task 1: ${Task1.caloriesOfTopOne(input)}")
    }

    @Test
    fun testDay1Task2() {
        val input = FileReader.getResource("year2022/day1.txt")
        println("2022 Day 1 Task 2: ${Task2.caloriesOfTopThree(input)}")
    }
}