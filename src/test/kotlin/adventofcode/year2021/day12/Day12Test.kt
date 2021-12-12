package adventofcode.year2021.day12

import adventofcode.FileReader
import org.junit.Test

class Day12Test {

    @Test
    fun testDay12Task1() {
        val lines = FileReader.getResource("year2021/day12.txt").lines()
        println("2021 Day 12 Task 1: ${Task1.numberOfPaths(lines)}")
    }

    @Test
    fun testDay12Task2() {
        val lines = FileReader.getResource("year2021/day12.txt").lines()
        println("2021 Day 12 Task 2: ${Task2.numberOfPaths(lines)}")
    }
}