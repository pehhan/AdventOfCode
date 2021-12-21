package adventofcode.year2021.day21

import adventofcode.FileReader
import org.junit.Test

class Day21Test {

    @Test
    fun testDay21Task1() {
        val input = FileReader.getResource("year2021/day21.txt")
        println("2021 Day 21 Task 1: ${Task1.result(input)}")
    }

//    @Test
//    fun testDay21Task2() {
//        val input = FileReader.getResource("year2021/day21.txt")
//        println("2021 Day 21 Task 2: ${Task2.result(input)}")
//    }
}