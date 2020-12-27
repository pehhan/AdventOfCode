package adventofcode.year2020.day24

import adventofcode.FileReader
import org.junit.Test

class Day24Test {

    @Test
    fun testDay24Task1() {
        val input = FileReader.getResource("year2020/day24.txt")
        println("2020 Day 24 Task 1: ${Task1.numberOfBlackTiles(input)}")
    }

    @Test
    fun testDay24Task2() {
        val input = FileReader.getResource("year2020/day24.txt")
        println("2020 Day 24 Task 2: ${Task2.numberOfBlackTiles(input)}")
    }
}