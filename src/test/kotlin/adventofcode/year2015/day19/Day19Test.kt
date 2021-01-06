package adventofcode.year2015.day19

import adventofcode.FileReader
import org.junit.Test

class Day19Test {

    @Test
    fun testDay19Task1() {
        val input = FileReader.getResource("year2015/day19.txt")
        println("2015 Day 19 Task 1: ${Task1.numberOfDistinctMolecules(input)}")
    }
}