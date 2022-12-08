package adventofcode.year2022.day8

import adventofcode.FileReader
import org.junit.Test

class Day8Test {

    @Test
    fun testDay8Task1() {
        val input = FileReader.getResource("year2022/day8.txt")
        println("2022 Day 8 Task 1: ${Task1.visibleTrees(input)}")
    }

    @Test
    fun testDay8Task2() {
        val input = FileReader.getResource("year2022/day8.txt")
        println("2022 Day 8 Task 2: ${Task2.highestScenicScore(input)}")
    }
}