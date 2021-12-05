package adventofcode.year2021.day5

import adventofcode.FileReader
import org.junit.Test

class Day5Test {

    @Test
    fun testDay5Task1() {
        val lines = FileReader.getResource("year2021/day5.txt").lines()
        println("2021 Day 5 Task 1: ${Task1.numberOfPointsWhereAtLeastTwoLinesOverlap(lines)}")
    }

    @Test
    fun testDay5Task2() {
        val lines = FileReader.getResource("year2021/day5.txt").lines()
        println("2021 Day 5 Task 2: ${Task2.numberOfPointsWhereAtLeastTwoLinesOverlap(lines)}")
    }
}