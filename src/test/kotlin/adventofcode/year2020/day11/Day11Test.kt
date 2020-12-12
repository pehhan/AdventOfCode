package adventofcode.year2020.day11

import adventofcode.FileReader
import org.junit.Test

class Day11Test {

    @Test
    fun testDay11Task1() {
        val input = FileReader.getResource("year2020/day11.txt")
        println("2020 Day 11 Task 1: ${Task1.seatsOccupied(input)}")
    }

    @Test
    fun testDay11Task2() {
        val input = FileReader.getResource("year2020/day11.txt")
        println("2020 Day 11 Task 2: ${Task2.seatsOccupied(input)}")
    }
}