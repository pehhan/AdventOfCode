package adventofcode.year2020.day5

import adventofcode.FileReader
import org.junit.Test

class Day5Test {

    @Test
    fun testDay5Task1() {
        val input = FileReader.getResource("year2020/day5.txt")
        println("2020 Day 5 Task 1: ${Task1.highestSeatId(input)}")
    }

    @Test
    fun testDay5Task2() {
        val input = FileReader.getResource("year2020/day5.txt")
        println("2020 Day 5 Task 2: ${Task2.findFreeSeatId(input)}")
    }
}