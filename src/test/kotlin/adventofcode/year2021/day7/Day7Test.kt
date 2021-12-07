package adventofcode.year2021.day7

import adventofcode.FileReader
import adventofcode.year2021.day6.numberOfFish
import org.junit.Test

class Day7Test {

    @Test
    fun testDay7Task1() {
        val input = FileReader.getResource("year2021/day7.txt")
        println("2021 Day 7 Task 1: ${Task1.fuel(input)}")
    }

    @Test
    fun testDay7Task2() {
        val input = FileReader.getResource("year2021/day7.txt")
        println("2021 Day 7 Task 2: ${Task2.fuel(input)}")
    }
}