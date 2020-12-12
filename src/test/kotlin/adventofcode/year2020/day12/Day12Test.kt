package adventofcode.year2020.day12

import adventofcode.FileReader
import org.junit.Test

class Day12Test {

    @Test
    fun testDay12Task1() {
        val input = FileReader.getResource("year2020/day12.txt")
        println("2020 Day 12 Task 1: ${Task1.distance(input)}")
    }

    @Test
    fun testDay12Task2() {
        val input = FileReader.getResource("year2020/day12.txt")
        println("2020 Day 12 Task 2: ${Task2.distance(input)}")
    }
}