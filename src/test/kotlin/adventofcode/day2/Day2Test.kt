package adventofcode.day2

import adventofcode.FileReader
import org.junit.Test

class Day2Test {

    @Test
    fun testDay2Task1() {
        val lines = FileReader.getResource("day2.txt").lines()
        println("Day 2 Task 1: ${Task1.numberOfValidPasswords(lines)}")
    }

    @Test
    fun testDay2Task2() {
        val lines = FileReader.getResource("day2.txt").lines()
        println("Day 2 Task 2: ${Task2.numberOfValidPasswords(lines)}")
    }
}