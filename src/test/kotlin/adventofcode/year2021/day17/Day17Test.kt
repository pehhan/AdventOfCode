package adventofcode.year2021.day17

import adventofcode.FileReader
import org.junit.Test

class Day17Test {

    @Test
    fun testDay17Task1() {
        val input = FileReader.getResource("year2021/day17.txt")
        println("2021 Day 17 Task 1: ${Task1.maxY(input)}")
    }

    @Test
    fun testDay17Task2() {
        val input = FileReader.getResource("year2021/day17.txt")
        println("2021 Day 17 Task 2: ${Task2.numberOfVelocities(input)}")
    }
}