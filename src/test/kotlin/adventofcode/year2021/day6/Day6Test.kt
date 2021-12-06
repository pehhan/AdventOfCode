package adventofcode.year2021.day6

import adventofcode.FileReader
import org.junit.Test

class Day6Test {

    @Test
    fun testDay6Task1() {
        val input = FileReader.getResource("year2021/day6.txt")
        println("2021 Day 6 Task 1: ${numberOfFish(input, 80)}")
    }

    @Test
    fun testDay6Task2() {
        val input = FileReader.getResource("year2021/day6.txt")
        println("2021 Day 6 Task 2: ${numberOfFish(input, 256)}")
    }
}