package adventofcode.year2020.day15

import adventofcode.FileReader
import org.junit.Test

class Day15Test {

    @Test
    fun testDay15Task1() {
        val input = FileReader.getResource("year2020/day15.txt")
        println("2020 Day 15 Task 1: ${Task1.getNumber(input)}")
    }

    @Test
    fun testDay15Task2() {
        val input = FileReader.getResource("year2020/day15.txt")
        println("2020 Day 15 Task 2: ${Task2.getNumber(input)}")
    }
}