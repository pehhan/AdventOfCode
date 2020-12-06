package adventofcode.year2019.day5

import adventofcode.FileReader
import org.junit.Test

class Day5Test {

    @Test
    fun testDay5Task1() {
        val input = FileReader.getResource("year2019/day5.txt")
        println("### 2019 Day 5 Task 1 ###")
        runProgram(input.split(",").map { it.toInt() }, 1)
    }

    @Test
    fun testDay5Task2() {
        val input = FileReader.getResource("year2019/day5.txt")
        println("### 2019 Day 5 Task 2 ###")
        runProgram(input.split(",").map { it.toInt() }, 5)
    }
}