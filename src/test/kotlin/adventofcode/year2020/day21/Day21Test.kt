package adventofcode.year2020.day21

import adventofcode.FileReader
import org.junit.Test

class Day21Test {

    @Test
    fun testDay21Task1() {
        val input = FileReader.getResource("year2020/day21.txt")
        println("2020 Day 21 Task 1: ${Task1.numberOfSafeIngredients(input)}")
    }

    @Test
    fun testDay21Task2() {
        val input = FileReader.getResource("year2020/day21.txt")
        println("2020 Day 21 Task 2: ${Task2.getDangerousIngredientList(input)}")
    }
}