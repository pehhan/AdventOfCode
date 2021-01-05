package adventofcode.year2015.day18

import adventofcode.FileReader
import org.junit.Test

class Day18Test {

    @Test
    fun testDay18Task1() {
        val input = FileReader.getResource("year2015/day18.txt")
        println("2015 Day 18 Task 1: ${Task1.numberOfLightsOn(input)}")
    }

    @Test
    fun testDay18Task2() {
        val input = FileReader.getResource("year2015/day18.txt")
        println("2015 Day 18 Task 2: ${Task2.numberOfLightsOn(input)}")
    }
}