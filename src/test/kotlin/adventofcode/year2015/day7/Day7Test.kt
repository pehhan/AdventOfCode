package adventofcode.year2015.day7

import adventofcode.FileReader
import org.junit.Test

@ExperimentalUnsignedTypes
class Day7Test {

    @Test
    fun testDay7Task1() {
        val input = FileReader.getResource("year2015/day7.txt")
        println("2015 Day 7 Task 1: ${Task1.signalToWireA(input)}")
    }

    @Test
    fun testDay7Task2() {
        val input = FileReader.getResource("year2015/day7.txt")
        println("2015 Day 7 Task 2: ${Task2.signalToWireA(input)}")
    }
}