package adventofcode.year2021.day25

import adventofcode.FileReader
import org.junit.Test

class Day25Test {

    @Test
    fun testDay25Task1() {
        val input = FileReader.getResource("year2021/day25.txt")
        println("2021 Day 25 Task 1: ${Task1.firstStepWithNoMoves(input)}")
    }
}