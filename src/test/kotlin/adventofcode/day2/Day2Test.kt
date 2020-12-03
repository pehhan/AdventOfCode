package adventofcode.day2

import adventofcode.FileReader
import org.junit.Test

class Day2Test {

    @Test
    fun testDay2Task1() {
        val input = FileReader.getResource("day2.txt").lines()
        println("Number of valid passwords: ${Task1.numberOfValidPasswords(input)}")
    }

    @Test
    fun testDay2Task2() {
        val input = FileReader.getResource("day2.txt").lines()
        println("Number of valid passwords: ${Task2.numberOfValidPasswords(input)}")
    }
}