package adventofcode.year2020.day18

import adventofcode.FileReader
import org.junit.Test

class Day18Test {

    @Test
    fun testDay18Task1() {
        val input = FileReader.getResource("year2020/day18.txt")
        println("2020 Day 18 Task 1: ${Task1.sumOfExpressions(input)}")
    }

    @Test
    fun testDay18Task2() {
        val input = FileReader.getResource("year2020/day18.txt")
        println("2020 Day 18 Task 2: ${Task2.sumOfExpressions(input)}")
    }
}