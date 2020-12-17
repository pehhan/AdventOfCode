package adventofcode.year2020.day17

import adventofcode.FileReader
import org.junit.Test

class Day17Test {

    @Test
    fun testDay17Task1() {
        val input = FileReader.getResource("year2020/day17.txt")
        println("2020 Day 17 Task 1: ${Task1.numberOfActiveCubes(input)}")
    }

    @Test
    fun testDay17Task2() {
        val input = FileReader.getResource("year2020/day17.txt")
        println("2020 Day 17 Task 2: ${Task2.numberOfActiveCubes(input)}")
    }
}