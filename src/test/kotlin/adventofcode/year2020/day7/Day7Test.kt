package adventofcode.year2020.day7

import adventofcode.FileReader
import org.junit.Test

class Day7Test {

    @Test
    fun testDay7Task1() {
        val input = FileReader.getResource("year2020/day7.txt")
        println("2020 Day 7 Task 1: ${Task1.numberOfBagsThatContainBag(input, "shiny gold bag")}")
    }

    @Test
    fun testDay7Task2() {
        val input = FileReader.getResource("year2020/day7.txt")
        println("2020 Day 7 Task 2: ${Task2.numberOfBagsThatContainBag(input, "shiny gold bag")}")
    }
}