package adventofcode.year2020.day23

import adventofcode.FileReader
import org.junit.Test

class Day23Test {

    @Test
    fun testDay23Task1() {
        val input = FileReader.getResource("year2020/day23.txt")
        println("2020 Day 23 Task 1: ${Task1.getCupLabels(input)}")
    }

    @Test
    fun testDay23Task2() {
        val input = FileReader.getResource("year2020/day23.txt")
        println("2020 Day 23 Task 2: ${Task2.getResult(input)}")
    }
}