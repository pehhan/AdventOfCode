package adventofcode.year2021.day22

import adventofcode.FileReader
import org.junit.Test

class Day22Test {

    @Test
    fun testDay22Task1() {
        val input = FileReader.getResource("year2021/day22.txt")
        println("2021 Day 22 Task 1: ${Task1.numberOfCubesOn(input)}")
    }

    @Test
    fun testDay22Task2() {
        val input = FileReader.getResource("year2021/day22.txt")
        println("2021 Day 22 Task 2: ${Task2.numberOfCubesOn(input)}")
    }
}