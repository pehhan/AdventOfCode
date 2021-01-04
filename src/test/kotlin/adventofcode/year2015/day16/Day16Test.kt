package adventofcode.year2015.day16

import adventofcode.FileReader
import org.junit.Test

class Day16Test {

    @Test
    fun testDay16Task1() {
        val input = FileReader.getResource("year2015/day16.txt")
        println("2015 Day 16 Task 1: ${Task1.whichAuntSue(input)}")
    }

    @Test
    fun testDay16Task2() {
        val input = FileReader.getResource("year2015/day16.txt")
        println("2015 Day 16 Task 2: ${Task2.whichAuntSue(input)}")
    }
}