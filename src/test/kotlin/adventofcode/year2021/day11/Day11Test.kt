package adventofcode.year2021.day11

import adventofcode.FileReader
import org.junit.Test

class Day11Test {

    @Test
    fun testDay11Task1() {
        val lines = FileReader.getResource("year2021/day11.txt").lines()
        println("2021 Day 11 Task 1: ${Task1.totalFlashes(lines, 100)}")
    }

//    @Test
//    fun testDay11Task2() {
//        val lines = FileReader.getResource("year2021/day11.txt").lines()
//        println("2021 Day 11 Task 2: ${Task2.totalFlashes(lines, 100)}")
//    }
}