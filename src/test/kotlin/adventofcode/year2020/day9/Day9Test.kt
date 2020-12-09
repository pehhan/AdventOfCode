package adventofcode.year2020.day9

import adventofcode.FileReader
import org.junit.Test

class Day9Test {

    @Test
    fun testDay9Task1() {
        val input = FileReader.getResource("year2020/day9.txt")
        println("2020 Day 9 Task 1: ${Task1.findInvalidNumber(input, 25)}")
    }

    @Test
    fun testDay9Task2() {
        val input = FileReader.getResource("year2020/day9.txt")
        println("2020 Day 9 Task 2: ${Task2.findEncryptionWeakness(input, 25)}")
    }
}