package adventofcode.year2023.day10

import adventofcode.FileReader
import org.junit.Test

class Day10Test {

    @Test
    fun testDay10Task1() {
        val input = FileReader.getResource("year2023/day10.txt")
        println("2023 Day 10 Task 1: ${Task1.steps(input)}")
    }

//    @Test
//    fun testDay10Task2() {
//        val input = FileReader.getResource("year2023/day10.txt")
//        println("2023 Day 10 Task 2: ${Task2.steps(input)}")
//    }
}