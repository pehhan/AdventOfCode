package adventofcode.year2020.day25

import adventofcode.FileReader
import org.junit.Test

class Day25Test {

    @Test
    fun testDay25Task1() {
        val input = FileReader.getResource("year2020/day25.txt")
        println("2020 Day 25 Task 1: ${Task1.findEncryptionKey(input)}")
    }

}