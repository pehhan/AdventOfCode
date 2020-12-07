package adventofcode.year2015.day1

object Task1 {
    fun findFloor(input: String): Int {
        return input.fold(0) { sum, char -> if (char == '(') sum + 1 else sum - 1 }
    }
}

object Task2 {
    fun findPosition(input: String): Int {
        var sum = 0

        return input.takeWhile {
            if (it == '(') sum++ else sum--
            sum >= 0
        }.length + 1
    }
}