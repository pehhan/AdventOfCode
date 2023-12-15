package adventofcode.year2023.day15

import adventofcode.FileReader
import org.junit.Test

class Day15Test {

    @Test
    fun testDay15Task1() {
        val input = FileReader.getResource("year2023/day15.txt")
        println("2023 Day 15 Task 1: ${Task1.result(input)}")
    }

//    @Test
//    fun testDay15Task2() {
//        val input = FileReader.getResource("year2023/day15.txt")
//        println("2023 Day 15 Task 2: ${Task2.steps(input)}")
//    }
}