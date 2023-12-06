package adventofcode.year2023.day6

import adventofcode.FileReader
import org.junit.Test

class Day6Test {

    @Test
    fun testDay6Task1() {
        val input = FileReader.getResource("year2023/day6.txt")
        println("2023 Day 6 Task 1: ${Task1.result(input)}")
    }

//    @Test
//    fun testDay6Task2() {
//        val input = FileReader.getResource("year2023/day6.txt")
//        println("2023 Day 6 Task 2: ${Task2.result(input)}")
//    }
}