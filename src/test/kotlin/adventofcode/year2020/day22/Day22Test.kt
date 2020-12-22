package adventofcode.year2020.day22

import adventofcode.FileReader
import org.junit.Test

class Day22Test {

    @Test
    fun testDay22Task1() {
        val input = FileReader.getResource("year2020/day22.txt")
        println("2020 Day 22 Task 1: ${Task1.getScoreForWinner(input)}")
    }

    @Test
    fun testDay22Task2() {
        val input = FileReader.getResource("year2020/day22.txt")
        println("2020 Day 22 Task 2: ${Task2.getScoreForWinner(input)}")
    }
}