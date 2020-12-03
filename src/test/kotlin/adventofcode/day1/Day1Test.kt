package adventofcode.day1

import adventofcode.FileReader
import org.junit.Test

class Day1Test {

    @Test
    fun testDay1Task1() {
        val input = FileReader.getResource("day1.txt").lines()
        println(Task1.findValue(input.map { it.toInt() }))
    }

    @Test
    fun testDay1Task2() {
        val input = FileReader.getResource("day1.txt").lines()
        println(Task2.findValue(input.map { it.toInt() }))
    }
}