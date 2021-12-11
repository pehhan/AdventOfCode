package adventofcode.year2021.day10

import adventofcode.FileReader
import org.junit.Test

class Day10Test {

    @Test
    fun testDay10Task1() {
        val lines = FileReader.getResource("year2021/day10.txt").lines()
        println("2021 Day 10 Task 1: ${Task1.syntaxErrorScore(lines)}")
    }

    @Test
    fun testDay10Task2() {
        val lines = FileReader.getResource("year2021/day10.txt").lines()
        println("2021 Day 10 Task 2: ${Task2.middleCompletionScore(lines)}")
    }
}