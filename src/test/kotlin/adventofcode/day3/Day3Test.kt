package adventofcode.day3

import adventofcode.FileReader
import org.junit.Test

class Day3Test {

    @Test
    fun testDay3Task1() {
        val input = FileReader.getResource("day3.txt").lines()
        println("Number of trees: ${Task1.numberOfTrees(input)}")
    }

    @Test
    fun testDay3Task2() {
        val input = FileReader.getResource("day3.txt").lines()
        println("Number of trees: ${Task2.numberOfTrees(input)}")
    }
}